package com.twlabs.model.settings;

import java.io.Serializable;

/**
 * Placeholder
 */
public class PlaceholderSettings implements Serializable {

    String suffix;
    String prefix;

    public boolean isEmpty() {
        return this.suffix == null || this.prefix == null || this.prefix.isEmpty()
                || this.suffix.isEmpty();
    }

    public PlaceholderSettings() {}

    public PlaceholderSettings(String match, String replace) {
        this.suffix = match;
        this.prefix = replace;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String match) {
        this.suffix = match;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String replace) {
        this.prefix = replace;
    }

}
