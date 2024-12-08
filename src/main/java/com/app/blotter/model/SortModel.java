package com.app.blotter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class SortModel {
    @JsonProperty("ColumnId")
    private String ColumnId;

    @JsonProperty("SortOrder")
    private String SortOrder;

    @JsonProperty("Uuid")
    private String Uuid;

    @JsonProperty("Source")
    private String Source;
}