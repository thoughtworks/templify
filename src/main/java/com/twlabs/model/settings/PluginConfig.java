package com.twlabs.model.settings;

import java.util.List;
import java.util.Map;

public class PluginConfig {

    public PluginConfig(List<StepsKindTemplate> steps) {
        this.steps = steps;
    }

    List<StepsKindTemplate> steps;

    Map<String, Object> settings;

    public PluginConfig() {}


    public List<StepsKindTemplate> getSteps() {
        return steps;
    }

    public void setSteps(List<StepsKindTemplate> steps) {
        this.steps = steps;
    }


    public Map<String, Object> getSettings() {
        return settings;
    }


    public void setSettings(Map<String, Object> settings) {
        this.settings = settings;
    }


}
