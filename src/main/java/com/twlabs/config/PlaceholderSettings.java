package com.twlabs.config;

import java.io.Serializable;

/**
 * Placeholder
 */
public class PlaceholderSettings implements Serializable {

    String suffix;
    String prefix;

    public boolean isEmpty() {
        return this.suffix == null || this.prefix == null || this.suffix.trim().isEmpty()
                || this.prefix.trim().isEmpty();
    }

    public PlaceholderSettings() {}

    public PlaceholderSettings(String match, String replace) {
        this.suffix = match;
        this.prefix = replace;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getPrefix() {
        return prefix;
    }

}
