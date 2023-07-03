package com.twlabs.model;

import com.twlabs.model.settings.Placeholder;

/**
 * Settings
 */
public class Settings {

    private Placeholder placeholder;

    public Settings() {
        
        placeholder = new Placeholder();
        placeholder.setPrefix("{{");
        placeholder.setSuffix("}}");

    }

    public Placeholder getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(Placeholder placeholder) {
        this.placeholder = placeholder;
    }

}
