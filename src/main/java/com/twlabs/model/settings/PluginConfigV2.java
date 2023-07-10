package com.twlabs.model.settings;

import java.util.List;

/**
 * PluginConfigKind
 */
public class PluginConfigV2 {

    public PluginConfigV2(List<StepsKindTemplate> steps) {
        this.steps = steps;
    }

    List<StepsKindTemplate> steps;

    public PluginConfigV2() {}


    public List<StepsKindTemplate> getSteps() {
        return steps;
    }

    public void setSteps(List<StepsKindTemplate> steps) {
        this.steps = steps;
    }

}
