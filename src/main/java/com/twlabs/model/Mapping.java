package com.twlabs.model;

import java.util.List;
import java.util.Objects;

public class Mapping {
    private String file;
    private String type;
    private String base_dir;
    private String base_test_dir;
    private Settings settings;

    private List<Placeholder> placeholders;


    public Mapping() {
    }


    public Mapping(String file, List<Placeholder> placePlaceholders) {
        this.file = file;
        this.placeholders = placePlaceholders;
        this.type = "file";
        this.base_dir = "";
        this.base_test_dir = "";
    }


    public Mapping(String sourceFile, List<Placeholder> placeholders, String type, String base_dir,
                   String base_test_dir) {
        this.file = sourceFile;
        this.placeholders = placeholders;
        this.type = type;
        this.base_dir = base_dir;
        this.base_test_dir = base_test_dir;
    }

    public String getFile() {
        if (this.file == null) {
            return this.getBase_dir();
        }
        return file;
    }


    public List<Placeholder> getPlaceholders() {
        return placeholders;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public void setPlaceholders(List<Placeholder> placeholders) {
        this.placeholders = placeholders;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBase_dir() {
        return base_dir;
    }

    public void setBase_dir(String base_dir) {
        this.base_dir = base_dir;
    }

    public String getBase_test_dir() {
        return base_test_dir;
    }

    public void setBase_test_dir(String base_test_dir) {
        this.base_test_dir = base_test_dir;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }


}
