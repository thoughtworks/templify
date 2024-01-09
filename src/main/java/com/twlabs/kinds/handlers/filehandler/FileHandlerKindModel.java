package com.twlabs.kinds.handlers.filehandler;

import java.util.List;

/**
 * FileHandlerKindSpec
 */
public class FileHandlerKindModel {

    private String kind;
    private List<FileHandlerKindModelSpec> spec;
    private FileHandlerKindModelMetadata metadata;

    public static class FileHandlerKindModelPlaceholder {

        String match;
        String replace;

        public String getMatch() {
            return match;
        }

        public void setMatch(String match) {
            this.match = match;
        }

        public String getReplace() {
            return replace;
        }

        public void setReplace(String replace) {
            this.replace = replace;
        }

    }
    public static class FileHandlerKindModelMetadata {

        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
    public static class FileHandlerKindModelSpec {

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
