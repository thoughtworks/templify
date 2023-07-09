package com.twlabs.model;

import java.util.Map;

public class Metadata {

    String prefix;
    String suffix;

    Map<String, String> extended;

    public Metadata(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public Metadata(String prefix, String suffix, Map<String, String> extended) {
        this.prefix = prefix;
        this.suffix = suffix;
        this.extended = extended;
    }

    public Metadata() {}

    public String getPrefix() {
        return this.prefix;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public Map<String, String> getExtended() {
        return extended;
    }

}
