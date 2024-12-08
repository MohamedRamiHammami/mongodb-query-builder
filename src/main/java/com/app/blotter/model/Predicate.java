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
public class Predicate {
    @JsonProperty("PredicateId")
    private String PredicateId;

    @JsonProperty("Inputs")
    private List<Object> Inputs;
}