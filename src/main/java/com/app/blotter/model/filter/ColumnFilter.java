package com.app.blotter.model.filter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class ColumnFilter {
    @JsonProperty("ColumnId")
    private String ColumnId;

    @JsonProperty("Predicate")
    private Predicate Predicate;

    @JsonProperty("Uuid")
    private String Uuid;

    @JsonProperty("Source")
    private String Source;
}