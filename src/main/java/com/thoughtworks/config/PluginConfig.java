package com.thoughtworks.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.thoughtworks.kinds.api.KindMappingTemplate;

// DTO class for the values of the maven-cookiecuter.yml
// Used by Jackson mapper
public class PluginConfig {

    List<KindMappingTemplate> steps;

    Map<String, Object> settings = new HashMap<String, Object>();

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
