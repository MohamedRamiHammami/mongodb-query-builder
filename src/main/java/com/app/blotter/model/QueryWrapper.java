package com.app.blotter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.query.Query;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class QueryWrapper {

    private Query basicQuery;
    private Query query;
}
