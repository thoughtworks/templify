package com.twlabs.model.settings;

import java.util.List;
import com.twlabs.model.Mapping;
import com.twlabs.model.Settings;

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


    public PluginConfig(List<Mapping> mappings){
        this.mappings = mappings;
    }

    public PluginConfig(Settings settings){
        this.settings = settings;
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
