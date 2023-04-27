package com.twlabs;

import java.util.List;

public class Mapping {
    private String file;
    private List<Placeholder> placeholders;


    public Mapping(){}

    public Mapping(String sourceFile, List<Placeholder> placeholders) {
        this.file = sourceFile;
        this.placeholders = placeholders;
    }


    public String getFile() {
        return file;
    }


    public List<Placeholder> getPlaceholders() {
        return placeholders;
    }

}
