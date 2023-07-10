package com.twlabs.model.settings;

import com.twlabs.model.Metadata;

public class StepsKindTemplate {

    private String kind;
    private Metadata metadata = new Metadata();
    private Spec spec;


    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }


    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public Spec getSpec() {
        return spec;
    }

    public void setSpec(Spec spec) {
        this.spec = spec;
    }
}
