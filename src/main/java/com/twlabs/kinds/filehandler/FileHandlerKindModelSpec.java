package com.twlabs.kinds.filehandler;

import java.util.List;

/**
 * FileHandlerKindSpec
 */
public class FileHandlerKindModelSpec {

    private List<String> files;
    private List<FileHandlerKindModelPlaceholder> placeholders;

    private String base_dir;

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public String getBase_dir() {
        return base_dir;
    }

    public void setBase_dir(String base_dir) {
        this.base_dir = base_dir;
    }

    public List<FileHandlerKindModelPlaceholder> getPlaceholders() {
        return placeholders;
    }

    public void setPlaceholders(List<FileHandlerKindModelPlaceholder> placeholders) {
        this.placeholders = placeholders;
    }


}

