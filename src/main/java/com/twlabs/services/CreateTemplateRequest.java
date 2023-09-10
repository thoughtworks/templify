package com.twlabs.services;

import java.io.File;
import org.apache.maven.plugin.logging.Log;
import com.twlabs.model.settings.PlaceholderSettings;
import com.twlabs.model.settings.PluginConfig;
import com.twlabs.services.logger.LoggerStrategyFactory;
import com.twlabs.services.logger.RunnerLogger;

/**
 * Initializes the Maven Cookiecutter with the given configuration.
 *
 * @field templateDir the directory where the generated template will be created.
 * @field baseDir the base directory of the base project.
 * @field buildDir directory where the intermediate build files will be stored
 * @field destDir directory where the final generated project will be stored
 * @field configuration location of the maven-cookiecutter.yml.
 * @field placeholder settings for the template variables
 * @field logger for logging messages during the generation process
 * @throws IllegalArgumentException if any of the parameters are null or empty
 */
public class CreateTemplateRequest {

    private static final String MAVEN_COOKIECUTTER_YML = "/maven-cookiecutter.yml";
    private String templateDir;
    private File baseDir;
    private String buildDir;
    private String destDir;
    private PluginConfig configuration;
    private PlaceholderSettings placeholder;
    private RunnerLogger logger;

    private CreateTemplateRequest() {}

    public String getConfigFilePath() {
        return this.baseDir + MAVEN_COOKIECUTTER_YML;
    }

    public String getTemplateDir() {
        return templateDir;
    }

    public void setTemplateDir(String templateDir) {
        this.templateDir = templateDir;
    }

    public PluginConfig getConfiguration() {
        return configuration;
    }

    public void setConfiguration(PluginConfig configuration) {
        this.configuration = configuration;
    }

    public static String getMavenCookiecutterYml() {
        return MAVEN_COOKIECUTTER_YML;
    }

    public PlaceholderSettings getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(PlaceholderSettings placeholder) {
        this.placeholder = placeholder;
    }

    public String getDestDir() {
        return destDir;
    }

    public void setDestDir(String destDir) {
        this.destDir = destDir;
    }

    public File getBaseDir() {
        return baseDir;
    }

    public void setBaseDir(File baseDir) {
        this.baseDir = baseDir;
    }

    public String getBuildDir() {
        return buildDir;
    }

    public void setBuildDir(String buildDir) {
        this.buildDir = buildDir;
    }

    public void setLogger(RunnerLogger logger) {
        this.logger = logger;
    }

    public void setLogger(Log logger) {
        this.logger = new LoggerStrategyFactory().create(logger);
    }

    public void setLogger() {
        this.logger = new LoggerStrategyFactory().create();
    }

    public RunnerLogger getLogger() {
        if (this.logger == null)
            this.logger = new LoggerStrategyFactory().create();

        return this.logger;
    }

    public static class CreateTemplateRequestBuilder {

        private CreateTemplateRequest request;

        public CreateTemplateRequestBuilder() {
            this.request = new CreateTemplateRequest();
        }

        public CreateTemplateRequestBuilder(CreateTemplateRequest request) {

            this.request = request;
        }

        public CreateTemplateRequestBuilder withTemplateDir(String templateDir) {
            this.request.setTemplateDir(templateDir);
            return this;
        }

        public CreateTemplateRequestBuilder withBaseDir(File baseDir) {
            this.request.setBaseDir(baseDir);
            return this;
        }

        public CreateTemplateRequestBuilder withBuildDir(String buildDir) {
            this.request.setBuildDir(buildDir);
            return this;
        }

        public CreateTemplateRequestBuilder withDestDir(String destDir) {
            this.request.setDestDir(destDir);
            return this;
        }

        public CreateTemplateRequestBuilder withConfiguration(PluginConfig configuration) {
            this.request.setConfiguration(configuration);
            return this;
        }

        public CreateTemplateRequestBuilder withPlaceholder(PlaceholderSettings placeholder) {
            this.request.setPlaceholder(placeholder);
            return this;
        }

        public CreateTemplateRequestBuilder withLogger(RunnerLogger logger) {
            this.request.setLogger(logger);
            return this;
        }


        public CreateTemplateRequestBuilder withLogger(Log logger) {
            this.request.setLogger(logger);
            return this;
        }

        public CreateTemplateRequest build() {
            return this.request;
        }

    }
}
