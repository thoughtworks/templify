package com.twlabs.model.settings;

import java.util.List;

/**
 * Spec
 */
public class Spec {

    List<String> files;
    List<PlaceholderV2> placeholders;

    public Spec() {}

    public Spec(List<String> files, List<PlaceholderV2> placeholders) {
        this.files = files;
        this.placeholders = placeholders;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public List<PlaceholderV2> getPlaceholders() {
        return placeholders;
    }

    public void setPlaceholders(List<PlaceholderV2> placeholders) {
        this.placeholders = placeholders;
    }


}

