package com.app.blotter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class AdaptableRequest {

    @JsonProperty("filters")
    private List<ColumnFilterDef> filters;

    @JsonProperty("sorts")
    private List<SortModel> sorts;

    private int startRow;
    private int endRow;
}