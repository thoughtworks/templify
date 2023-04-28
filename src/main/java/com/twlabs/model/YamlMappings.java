package com.twlabs.model;

import java.util.List;

/**
 * YamlMappings
 */
public class YamlMappings {
    private List<Mapping> mappings;

    public YamlMappings() {}

    public YamlMappings(List<Mapping> mappings) {
        this.mappings = mappings;
    }

    public List<Mapping> getMappings() {
        return mappings;
    }

    public void setMappings(List<Mapping> mappings) {
        this.mappings = mappings;
    }
}
