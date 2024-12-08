package com.app.blotter.model;


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
    NotBetween("NotBetween");

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