package com.thoughtworks.kinds.handlers.base;

public class KindPlaceholder {

    public String match;
    public String replace;

    public KindPlaceholder() {}

    public KindPlaceholder(String match, String replace) {
        this.match = match;
        this.replace = replace;
    }

    public String getMatch() {
        return match;
    }

    public String getReplace() {
        return replace;
    }
}
