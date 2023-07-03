package com.twlabs.model.settings;

/**
 * Placeholder
 */
public class Placeholder {

    private String suffix;
    private String prefix;

    public Placeholder() {}

    public Placeholder(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

}
