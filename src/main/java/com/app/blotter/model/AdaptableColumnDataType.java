package com.app.blotter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AdaptableColumnDataType {
    @JsonProperty("String")
    STRING,
    @JsonProperty("Number")
    NUMBER,
    @JsonProperty("Boolean")
    BOOLEAN,
    @JsonProperty("Date")
    DATE,
    @JsonProperty("Object")
    OBJECT,
    @JsonProperty("StringArray")
    STRING_ARRAY,
    @JsonProperty("NumberArray")
    NUMBER_ARRAY,
    @JsonProperty("TupleNumberArray")
    TUPLE_NUMBER_ARRAY,
    @JsonProperty("ObjectNumberArray")
    OBJECT_NUMBER_ARRAY,
    @JsonProperty("Unknown")
    UNKNOWN
}