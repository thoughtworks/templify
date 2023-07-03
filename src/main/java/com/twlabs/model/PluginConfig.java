package com.twlabs.model;

import java.util.List;

/**
 * YamlMappings
 */
public class PluginConfig {
    private List<Mapping> mappings;

    private Settings settings;


    public PluginConfig() {}

    public List<Mapping> getMappings() {
        return mappings;
    }

    public PluginConfig(Settings settings, List<Mapping> mappings) {
        this.mappings = mappings;
        this.settings = settings;
    }



    public void setMappings(List<Mapping> mappings) {
        this.mappings = mappings;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }



}
