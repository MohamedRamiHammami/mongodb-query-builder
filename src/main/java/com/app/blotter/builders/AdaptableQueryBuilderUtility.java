package com.app.blotter.builders;

import com.app.blotter.model.AdaptableRequest;
import com.app.blotter.model.filter.ColumnFilterDef;
import com.app.blotter.model.queryast.QueryAST;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.app.blotter.builders.QueryBuilder.*;

public class AdaptableQueryBuilderUtility {

    public static Query buildQuery(AdaptableRequest request) {
        Query query = new Query();

        List<ColumnFilterDef> filters = request.getFilters().stream().toList();
        filters.forEach(filter -> query.addCriteria(buildCriteria(filter)));

        List<Object> queryAST = request.getQueryAst();

        queryAST.forEach(filter -> {
                    Map<Object, Object> map = (LinkedHashMap<Object, Object>) filter;
                    QueryAST ast = convertToQueryAST(map);
                    Criteria criteriaDefinition = buildQueryASTWherePart(ast);
                    query.addCriteria(criteriaDefinition);
                }
        );

        if (request.getSorts() != null) {
            request.getSorts().forEach(sort -> query.with(org.springframework.data.domain.Sort.by(
                    "Asc".equalsIgnoreCase(sort.getSortOrder()) ?
                            org.springframework.data.domain.Sort.Order.asc(sort.getColumnId()) :
                            org.springframework.data.domain.Sort.Order.desc(sort.getColumnId())
            )));
        }
        return query;
    }
}