package com.twlabs;

import com.twlabs.exceptions.FileHandlerException;
import com.twlabs.handlers.*;
import com.twlabs.interfaces.ConfigReader;
import com.twlabs.interfaces.FileHandler;
import com.twlabs.model.Mapping;
import com.twlabs.model.Placeholder;
import com.twlabs.model.PluginConfig;
import com.twlabs.model.Settings;
import com.twlabs.services.YamlReader;
import org.apache.commons.io.FilenameUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;


@Mojo(name = "cutter", defaultPhase = LifecyclePhase.NONE)
public class CookieCutterMojo extends AbstractMojo {

    private static final String BUILD_TEMPLATE_DIR = "/template";

    /**
     * This references to the root folder of the module/project (the location where the current
     * pom.xml file is located)
     */
    @Parameter(defaultValue = "${project.basedir}", required = true, readonly = true)
    private File baseDir;

    /**
     * This represents by default the target folder
     */
    @Parameter(defaultValue = "${project.build.directory}", required = true, readonly = true)
    private File buildDir;

    private YamlHandler yamlHandler = new YamlHandler();
    private XMLHandler xmlHandler = new XMLHandler();
    private JsonHandler jsonHandler = new JsonHandler();
    private JavaHandler javaHandler = new JavaHandler();
    private PlainTextHandler plainTextHandler = new PlainTextHandler();

    private ConfigReader reader = new YamlReader();


    private com.twlabs.model.settings.Placeholder placeholderSettings;

    public void execute() throws MojoExecutionException, MojoFailureException {

        getLog().info("Brace yourself! starting cookiecutter-templater-maven-plugin!!");


        getLog().warn("Checking if the template dir exists" + getTemplateDir());
        if (deleteTemplateIfexist(getTemplateDir())) {
            getLog().warn("Old template directory was found and it was removed!!");
        }

        copyProjectTo(getTemplateDir());

        getLog().warn("Project build dir:" + this.buildDir.getPath());
        getLog().warn("Backstage template dir:" + getTemplateDir());

        getLog().warn("Reading settings from config file!");
        getSettings();

        if (this.placeholderSettings.getPrefix() == "{{"
                && this.placeholderSettings.getSuffix() == "}}") {
            // "Using default placeholder settings!! -> Prefix:{{ and Suffix: }}"
            getLog().warn("Using default placeholder settings!! -> Prefix:"
                    + this.placeholderSettings.getPrefix() + " and Suffix: "
                    + this.placeholderSettings.getSuffix());
        } else {
            getLog().warn("Using custom placeholder settings!! -> Prefix:"
                    + this.placeholderSettings.getPrefix() + " and Suffix: "
                    + this.placeholderSettings.getSuffix());
            javaHandler.setHandler(this.placeholderSettings);

        }

        getLog().warn("Starting placheholders");
        setPlaceHolders();
        getLog().warn("End to config placeholders");

    }


    private boolean deleteTemplateIfexist(String templateDir) {
        try {
            if (FileUtils.fileExists(templateDir)) {
                FileUtils.deleteDirectory(templateDir);
                return true;
            }
        } catch (IOException e) {
            getLog().error("It was not possible to remove directory: " + templateDir, e);

        }
        return false;
    }


    /**
     * Registry pattern for open-closed to set, add, remove, expand the handlers outside of mojo
     * logic withouth changing setPlaceHolders code block.
     */
    private Map<String, FileHandler> getFileHandlerRegistry() {
        return Map.of("xml", this.xmlHandler, "yaml", this.yamlHandler, "yml", this.yamlHandler,
                "json", this.jsonHandler, "java", this.javaHandler, "txt", this.plainTextHandler);
    }

    private String getTemplateDir() {
        return this.buildDir.getPath() + BUILD_TEMPLATE_DIR;
    }

    private PluginConfig getConfig() throws IOException {

        String configFile = getTemplateDir() + "/maven-cookiecutter.yml";

        getLog().warn("Template file: " + configFile);

        PluginConfig config = reader.read(configFile);

        return config;
    }

    private void getSettings() {

        try {
            if (getConfig().getSettings() == null) {
                this.placeholderSettings = new Settings().getPlaceholder();
            } else {
                this.placeholderSettings = getConfig().getSettings().getPlaceholder();
            }
        } catch (Exception e) {
            getLog().error("Error to read the settings from the config file", e);
            ;
        }
    }

    private void setPlaceHolders() {

        try {
            getConfig().getMappings().forEach(this::setPlaceHolder);

        } catch (IOException e) {
            getLog().error("Error to read the config file", e);
            ;
        }
    }

    private void setPlaceHolder(Mapping mapping) {

        com.twlabs.model.settings.Placeholder placeHolderSettings = getPlaceHolderSettings(mapping);


        String file = mapping.getFile();
        String filePath = getTemplateDir() + "/" + file;
        String type = getMappingType(mapping);

        getLog().info("Finding handler for type: " + type + " and file: " + filePath);
        FileHandler handler = getHandler(type);

        getLog().info("Start placeholder for: " + filePath + " and handler " + handler.getClass().getName());
        for (Placeholder placeholder : mapping.getPlaceholders()) {
            try {
                handler.replace(filePath, placeholder.getQuery(),
                        placeHolderSettings.getPrefix() + placeholder.getName()
                                + placeHolderSettings.getSuffix());
            } catch (FileHandlerException e) {
                e.printStackTrace();
                getLog().error("Error to replace placeholders", e);
            }
        }
    }

    private FileHandler getHandler(String type) {
        Map<String, FileHandler> handlersRegistry = getFileHandlerRegistry();
        FileHandler handler = handlersRegistry.get(type);
        if (handler == null) {
            getLog().warn("No handler found for type: " + type + ". Defaulting to PlainTextHandler");
            handler = this.plainTextHandler;
        }
        return handler;
    }

    private com.twlabs.model.settings.Placeholder getPlaceHolderSettings(Mapping mapping) {
        getLog().info("Checking if the mapping has overridden placeholder settings, otherwise use the default settings");
        return Objects.nonNull(mapping.getSettings()) ? mapping.getSettings().getPlaceholder() : this.placeholderSettings;
    }


    private String getMappingType(Mapping mapping) {
        String type = mapping.getType();
        if (type == null) {
            // get extension of file but check if it doesn't have extension then return txt as default
            type = FilenameUtils.getExtension(mapping.getFile());
            if (type == null || type.isEmpty()) {
                type = "txt";
            }
        }
        return type;
    }


    /**
     * Copy base dir to the dest folder to the replaces
     *
     * @throws MojoExecutionException
     */
    private void copyProjectTo(String dest) throws MojoExecutionException {
        try {

            FileUtils.copyDirectoryStructure(this.baseDir, new File(dest));

        } catch (IOException e) {
            throw new MojoExecutionException(
                    "Something went wrong while copying the project to the template folder.", e);
        }
    }


}
