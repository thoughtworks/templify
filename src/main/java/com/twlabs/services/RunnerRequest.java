package com.twlabs.services;

import java.io.File;
import org.apache.maven.plugin.logging.Log;
import com.twlabs.model.settings.PlaceholderSettings;
import com.twlabs.model.settings.PluginConfig;
import com.twlabs.services.logger.LoggerStrategyFactory;
import com.twlabs.services.logger.RunnerLogger;

/**
 * RunnerRecord
 */
public class RunnerRequest {

    private static final String MAVEN_COOKIECUTTER_YML = "/maven-cookiecutter.yml";
    private String templateDir;
    private File baseDir;
    private String buildDir;
    private String destDir;
    private PluginConfig configuration;
    private PlaceholderSettings placeholder;
    private RunnerLogger logger;

    public String getConfigFilePath() {
        return this.templateDir + MAVEN_COOKIECUTTER_YML;
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

    public void setLogger(Log logger) {
        this.logger  = new LoggerStrategyFactory().create(logger);
    }

    public void setLogger() {
        this.logger  = new LoggerStrategyFactory().create();
    }

    public RunnerLogger getLogger() {
        if(this.logger == null)
            this.logger  = new LoggerStrategyFactory().create();

        return this.logger;
    }
}
