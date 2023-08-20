package com.twlabs.kinds.filehandler;

import java.util.List;

/**
 * FileHandlerKindSpec
 */
public class FileHandlerKindModel {

    private String kind;
    private List<FileHandlerKindModelSpec> spec;
    private FileHandlerKindModelMetadata metadata;

    public FileHandlerKindModelMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(FileHandlerKindModelMetadata metadata) {
        this.metadata = metadata;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public List<FileHandlerKindModelSpec> getSpec() {
        return spec;
    }

    public void setSpec(List<FileHandlerKindModelSpec> spec) {
        this.spec = spec;
    }

}
