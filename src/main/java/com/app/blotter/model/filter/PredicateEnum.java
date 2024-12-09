package com.app.blotter.model.filter;


import java.util.Arrays;

public enum PredicateEnum {
    GreaterThan("GreaterThan"),
    LessThan("LessThan"),
    Blanks("Blanks"),
    NonBlanks("NonBlanks"),
    Equals("Equals"),
    NotEquals("NotEquals"),
    Contains("Contains"),
    NotContains("NotContains"),
    Values("Values"),
    StartsWith("StartsWith"),
    EndsWith("EndsWith"),
    Positive("Positive"),
    Negative("Negative"),
    Zero("Zero"),
    Between("Between"),
    NotBetween("NotBetween"),

    COL("COL"),
    EQ("EQ"),
    NEQ("NEQ"),
    GT("GT"),
    LT("LT"),
    GTE("GTE"),
    OR("OR"),
    Or("Or"),
    AND("AND"),
    And("And"),
    NOT("NOT"),
    Not("Not"),
    BETWEEN("BETWEEN"),
    IN("IN"),
    In("In"),
    IS_BLANK("IS_BLANK"),
    IsBlank("IsBlank"),
    CONTAINS("CONTAINS"),
    STARTS_WITH("STARTS_WITH"),
    ENDS_WITH("ENDS_WITH"),
    FROM_EUROPE("FROM_EUROPE");

    private final String name;

    PredicateEnum(String name) {
        this.name = name;
    }

    public static PredicateEnum fromName(String name) {
        return Arrays.stream(values())
                .filter(predicate -> predicate.name.equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported predicate: " + name));
    }
}