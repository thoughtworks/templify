package com.twlabs;

import java.util.List;

/**
 * ConfigMappings
 */
public interface ConfigMappings {

    public List<Mapping> getMappings();

    public void setMappings(List<Mapping> mappings);


    public static class Mapping {
        private String sourceFile;
        private String targetFile;
        private List<Translation> translations;

        public Mapping(String sourceFile, String targetFile, List<Translation> translations) {
            this.sourceFile = sourceFile;
            this.targetFile = targetFile;
            this.translations = translations;
        }

        public String getSourceFile() {
            return sourceFile;
        }

        public String getTargetFile() {
            return targetFile;
        }

        public List<Translation> getTranslations() {
            return translations;
        }
    }

    public static class Translation {
        private String sourceKey;
        private String targetValue;

        public Translation(String sourceKey, String targetValue) {
            this.sourceKey = sourceKey;
            this.targetValue = targetValue;
        }

        public String getSourceKey() {
            return sourceKey;
        }

        public String getTargetValue() {
            return targetValue;
        }
    }

}
