package com.twlabs.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.twlabs.kinds.api.KindMappingTemplate;

public class PluginConfig {

    public PluginConfig(List<KindMappingTemplate> steps) {
        this.steps = steps;
    }

    List<KindMappingTemplate> steps;

    Map<String, Object> settings = new HashMap<String, Object>();

    public PluginConfig() {}


    public List<KindMappingTemplate> getSteps() {
        return steps;
    }

    public void setSteps(List<KindMappingTemplate> steps) {
        this.steps = steps;
    }


    public Map<String, Object> getSettings() {
        return settings;
    }


    public void setSettings(Map<String, Object> settings) {
        this.settings = settings;
    }


}
