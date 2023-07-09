package com.twlabs.model;

import java.util.Map;

public class Options {

    String placeholderPrefix;
    String placeholderSuffix;

    Map<String, String> extended;

    public Options(String prefix, String suffix) {
        this.placeholderPrefix = prefix;
        this.placeholderSuffix = suffix;
    }

    public Options(String prefix, String suffix, Map<String, String> extended) {
        this.placeholderPrefix = prefix;
        this.placeholderSuffix = suffix;
        this.extended = extended;
    }

    public String getPlaceholderPrefix() {
        return this.placeholderPrefix;
    }

    public String getPlaceholderSuffix() {
        return this.placeholderSuffix;
    }

    public Map<String, String> getExtended() {
        return extended;
    }

}
