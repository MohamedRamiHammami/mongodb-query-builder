package com.app.blotter.builders;

import com.app.blotter.model.AdaptableColumnDataType;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

public class CriteriaUtils {

    public static String containsExpression(Object input0) {
        return ".*" + input0 + ".*";
    }

    public static Criteria regex(String columnId, String regex) {
        return Criteria.where(columnId).regex(regex, "i");
    }

    public static Criteria in(String columnId, List<Object> inputs) {
        return Criteria.where(columnId).in(inputs);
    }

    public static Criteria is(String columnId, Object input0) {
        return Criteria.where(columnId).is(input0);
    }

    public static Criteria lessThan(String columnId, Object input0) {
        return Criteria.where(columnId).lt(input0);
    }

    public static Criteria between(String columnId, List<Object> inputs) {
        return Criteria.where(columnId).gte(inputs.get(0)).lte(inputs.get(1));
    }

    public static Criteria notEquals(String columnId, Object input0) {
        return Criteria.where(columnId).ne(input0);
    }

    public static Criteria greaterThan(String columnId, Object input0) {
        return Criteria.where(columnId).gt(input0);
    }

    public static Object parseInput(AdaptableColumnDataType columnType, Object input) {
        try {
            return switch (columnType) {
                case STRING -> input.toString();
                case NUMBER -> Double.valueOf(input.toString());
                case BOOLEAN -> Boolean.valueOf(input.toString());
                case DATE -> java.time.Instant.parse(input.toString()); // Adjust date parsing as needed.
                default -> input;
            };
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse input: " + input + " as type: " + columnType, e);
        }
    }

    public static List<Object> parseInputs(AdaptableColumnDataType columnType, List<Object> inputs) {
        return inputs.stream().map(input -> parseInput(columnType, input)).toList();
    }
}
