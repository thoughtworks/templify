package com.twlabs.model;

/**
 * Options
 */
public class Options {

    String placeholderPrefix;
    String placeholderSuffix;

    public Options(String prefix, String suffix) {
        this.placeholderPrefix = prefix;
        this.placeholderSuffix = suffix;
    }

    public String getPlaceholderPrefix() {
        return this.placeholderPrefix;
    }

    public String getPlaceholderSuffix() {
        return this.placeholderSuffix;
    }

}
