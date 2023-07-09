package com.twlabs.model;

import java.util.Map;

public class Options {

    String placeholderPrefix;
    String placeholderSuffix;

    Map<String, String> extensions;

    public Options(String prefix, String suffix) {
        this.placeholderPrefix = prefix;
        this.placeholderSuffix = suffix;
    }

    public Options(String prefix, String suffix, Map<String, String> extensions) {
        this.placeholderPrefix = prefix;
        this.placeholderSuffix = suffix;
        this.extensions = extensions;
    }

    public String getPlaceholderPrefix() {
        return this.placeholderPrefix;
    }

    public String getPlaceholderSuffix() {
        return this.placeholderSuffix;
    }

    public Map<String, String> getExtensions() {
        return extensions;
    }

}
