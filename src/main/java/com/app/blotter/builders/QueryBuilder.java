package com.app.blotter.builders;

import com.app.blotter.model.AdaptableColumnDataType;
import com.app.blotter.model.filter.ColumnFilterDef;
import com.app.blotter.model.filter.Predicate;
import com.app.blotter.model.filter.PredicateEnum;
import com.app.blotter.model.queryast.QueryAST;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.app.blotter.builders.CriteriaUtils.*;


public class QueryBuilder {

    public QueryBuilder() {
        throw new IllegalArgumentException("Utility class");
    }

    public static Criteria buildCriteria(ColumnFilterDef filterDef) {
        String columnId = filterDef.getColumnFilter().getColumnId();
        Predicate predicate = filterDef.getColumnFilter().getPredicate();
        AdaptableColumnDataType columnType = filterDef.getDataType();
        List<Object> inputs = predicate.getInputs();
        inputs = parseInputs(columnType, inputs);

        PredicateEnum predicateEnum = PredicateEnum.fromName(predicate.getPredicateId());

        return switch (predicateEnum) {
            case GreaterThan -> greaterThan(columnId, inputs.get(0));
            case LessThan -> lessThan(columnId, inputs.get(0));
            case Blanks -> is(columnId, null);
            case NonBlanks -> notEquals(columnId, null);
            case Equals -> is(columnId, inputs.get(0));
            case NotEquals -> notEquals(columnId, inputs.get(0));
            case Contains -> regex(columnId, containsExpression(inputs.get(0)));
            case NotContains -> Criteria.where(columnId).not().regex(containsExpression(inputs.get(0)), "i");
            case Values -> in(columnId, inputs);
            case StartsWith -> regex(columnId, "^" + inputs.get(0));
            case EndsWith -> regex(columnId, inputs.get(0) + "$");
            case Positive -> greaterThan(columnId, 0);
            case Negative -> lessThan(columnId, 0);
            case Zero -> is(columnId, 0);
            case Between -> between(columnId, inputs);
            case NotBetween -> lessThan(columnId, inputs.get(0)).orOperator(greaterThan(columnId, inputs.get(1)));
            default -> throw new IllegalArgumentException("Unsupported predicate: " + predicateEnum);
        };
    }

    public static Criteria buildQueryASTWherePart(Object queryAST) {
        if (queryAST == null) {
            return new Criteria();
        }

        if (queryAST instanceof String query) {
            return Criteria.where(query);
        }

        if (queryAST instanceof Number) {
            return Criteria.where(queryAST.toString());
        }

        if (queryAST instanceof List) {
            List<Object> list = (List<Object>) queryAST;
            return buildAndCriterias(list);
        }

        if (queryAST instanceof QueryAST ast) {
            List<Object> args = ast.getArgs();

            List<Object> processedArgs = args.stream().map(arg -> arg instanceof QueryAST ? buildQueryASTWherePart(arg) : arg).collect(Collectors.toList());

            return processQueryAST(ast.getType(), processedArgs);
        }
        if (queryAST instanceof LinkedHashMap<?, ?> map) {
            QueryAST ast = convertToQueryAST(map);
            return processQueryAST(ast.getType(), ast.getArgs());
        }

        throw new IllegalArgumentException("Unsupported queryAST type: " + queryAST.getClass());
    }

    public static QueryAST convertToQueryAST(Map<?, ?> map) {
        return QueryAST.builder().type((String) map.get("type")).args((List<Object>) map.get("args")).build();
    }

    private static Criteria processQueryAST(String type, List<Object> args) {
        final Object value = args.get(0);
        return switch (type.toUpperCase()) {
            case "COL" -> Criteria.where((String) value);
            case "EQ" -> Criteria.where(value.toString()).is(args.get(1));
            case "NEQ" -> Criteria.where(value.toString()).ne(args.get(1));
            case "GT" -> Criteria.where(value.toString()).gt(args.get(1));
            case "LT" -> Criteria.where(value.toString()).lt(args.get(1));
            case "GTE" -> Criteria.where(value.toString()).gte(args.get(1));
            case "LTE" -> Criteria.where(value.toString()).lte(args.get(1));
            case "OR" -> buildOrCriterias(args);
            case "AND" -> buildAndCriterias(args);
            case "NOT" -> new Criteria().not().is(value);
            case "BETWEEN" -> Criteria.where(value.toString()).gte(args.get(1)).lte(args.get(2));
            case "IN" -> Criteria.where(value.toString()).in(args.subList(1, args.size()));
            case "IS_BLANK" -> Criteria.where(value.toString()).is(null);
            case "CONTAINS" -> Criteria.where(value.toString()).regex(".*" + args.get(1) + ".*");
            case "STARTS_WITH" -> Criteria.where(value.toString()).regex("^" + args.get(1));
            case "ENDS_WITH" -> Criteria.where(value.toString()).regex(args.get(1) + "$");
            case "FROM_EUROPE" ->
                // Example for custom logic
                    new Criteria().orOperator(Criteria.where("country").is("France"), Criteria.where("country").is("Germany"), Criteria.where("country").is("Italy"));
            default -> throw new IllegalArgumentException("Unsupported queryAST type: " + type);
        };
    }

    private static Criteria buildAndCriterias(List<Object> args) {
        List<Criteria> criteriaList = args.stream().map(QueryBuilder::buildQueryASTWherePart).toList();
        return new Criteria().andOperator(criteriaList);
    }

    private static Criteria buildOrCriterias(List<Object> args) {
        List<Criteria> criteriaList = args.stream().map(QueryBuilder::buildQueryASTWherePart).toList();
        return new Criteria().orOperator(criteriaList);
    }
}
