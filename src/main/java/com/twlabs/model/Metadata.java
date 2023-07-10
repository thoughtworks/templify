package com.twlabs.model;

import java.util.HashMap;
import java.util.Map;

public class Metadata {

    String prefix;
    String suffix;

    Map<String, String> options;

    public Metadata(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public Metadata(String prefix, String suffix, Map<String, String> extended) {
        this.prefix = prefix;
        this.suffix = suffix;
        this.options = extended;
    }

    public Metadata() {
        this.options = new HashMap<String, String>();
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public Map<String, String> getOptions() {
        return options;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public void setOptions(Map<String, String> options) {
        this.options = options;
    }

}
