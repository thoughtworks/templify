package com.twlabs.model.settings;

import java.util.List;
import java.util.Map;

/**
 * This class represents a template for a StepsKind object.
 * 
 * A StepsKind object represents a kind of step that can be performed in the template creation
 * process.
 * 
 * KISS principle, make settings as simple as possible. Avoid major breaks in the user interface
 * Steps must be simple to define using .yaml file.
 *
 * @kind {String} must be the name of the interface, ex: FileHandler
 * @metadata {Map} metadata for the step, it's a map of anything.
 * @spec {Map} input for the step, it's a map of anything as well.
 *
 *       Note: It's important that each interface validate spec and metadata.
 * @see FileHandlerKind
 */
public class StepsKindTemplate {

    private String kind;

    private Map<String, Object> metadata;
    private List<Map<String, Object>> specs;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public List<Map<String, Object>> getSpecs() {
        return specs;
    }

    public void setSpecs(List<Map<String, Object>> spec) {
        this.specs = spec;
    }



}
