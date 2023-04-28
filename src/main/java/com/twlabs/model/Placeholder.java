package com.twlabs.model;

/**
 * Placeholder
 */
public class Placeholder {
    private String query;
    private String name;


    public Placeholder() {}

    public Placeholder(String sourceKey, String targetValue) {
        this.query = sourceKey;
        this.name = targetValue;
    }

    public String getQuery() {
        return query;
    }

    public String getName() {
        return name;
    }
}
