package com.app.blotter.model.queryast;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class QueryAST {
    private String type;
    private List<Object> args;
    private List<Integer> range;
    private String metaInfo;
}
