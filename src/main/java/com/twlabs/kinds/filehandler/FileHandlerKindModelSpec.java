package com.twlabs.kinds.filehandler;

import java.util.List;

/**
 * FileHandlerKindSpec
 */
public class FileHandlerKindModelSpec {

    private List<String> files;
    private List<FileHandlerKindModelSpec> placeholders;

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public List<FileHandlerKindModelSpec> getPlaceholders() {
        return placeholders;
    }

    public void setPlaceholders(List<FileHandlerKindModelSpec> placeholders) {
        this.placeholders = placeholders;
    }
}

