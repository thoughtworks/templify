package com.twlabs.model.settings;

/**
 * PlaceholderV2
 */
public class PlaceholderV2 {

    public PlaceholderV2() {}

    public PlaceholderV2(String match, String replace) {
        this.match = match;
        this.replace = replace;
    }

    String match, replace;

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public String getReplace() {
        return replace;
    }

    public void setReplace(String replace) {
        this.replace = replace;
    }

}

