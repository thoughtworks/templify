package com.twlabs.model;

import java.util.List;

/**
 * YamlMappings
 */
public class PluginConfig {
    private List<Mapping> mappings;

    public PluginConfig() {}

    public List<Mapping> getMappings() {
        return mappings;
    }

    public void setMappings(List<Mapping> mappings) {
        this.mappings = mappings;
    }

    public PluginConfig(List<Mapping> mappings) {
        this.mappings = mappings;
    }

}
