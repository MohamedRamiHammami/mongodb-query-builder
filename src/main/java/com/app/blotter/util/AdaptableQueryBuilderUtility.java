package com.app.blotter.util;

import com.app.blotter.model.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.app.blotter.util.CriteriaUtils.*;

@Component
public class AdaptableQueryBuilderUtility {

    public QueryWrapper buildQuery(AdaptableRequest request) {
        Query basicQuery = basicQuery(request);

        Query query = Query.of(basicQuery);
        query.skip(request.getStartRow());
        query.limit(request.getEndRow() - request.getStartRow());

        return QueryWrapper.builder().basicQuery(basicQuery).query(query).build();
    }

    private Query basicQuery(AdaptableRequest request) {
        Query query = new Query();

        List<ColumnFilterDef> filters = request.getFilters().stream().toList();
        filters.forEach(filter -> query.addCriteria(buildCriteria(filter)));

        if (request.getSorts() != null) {
            request.getSorts().forEach(sort -> query.with(org.springframework.data.domain.Sort.by(
                    "Asc".equalsIgnoreCase(sort.getSortOrder()) ?
                            org.springframework.data.domain.Sort.Order.asc(sort.getColumnId()) :
                            org.springframework.data.domain.Sort.Order.desc(sort.getColumnId())
            )));
        }
        return query;
    }

    private Criteria buildCriteria(ColumnFilterDef filterDef) {
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
        };
    }


}