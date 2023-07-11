package com.twlabs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.FileUtils;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import com.twlabs.exceptions.FileHandlerException;
import com.twlabs.injetor.ContextDependencyInjection;
import com.twlabs.interfaces.ConfigReader;
import com.twlabs.interfaces.FileHandlerKind;
import com.twlabs.model.settings.PluginConfig;
import com.twlabs.model.settings.StepsKindTemplate;


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

    @Inject
    @Named("yaml")
    private FileHandlerKind yamlHandler;

    @Inject
    @Named("xml")
    private FileHandlerKind xmlHandler;

    @Inject
    @Named("json")
    private FileHandlerKind jsonHandler;

    @Inject
    @Named("java")
    private FileHandlerKind javaHandler;

    @Inject
    private ConfigReader reader;

    private PluginConfig config;


    public void execute() throws MojoExecutionException, MojoFailureException {

        getLog().info("Brace yourself! starting cookiecutter-templater-maven-plugin!!");

        Injector injector = Guice.createInjector(new ContextDependencyInjection());
        injector.injectMembers(this);

        // create target/template dir
        copyProjectTo(getTemplateDir());

        // load and validate config file before copy project
        // BUG settings are read from config file in template folder
        loadConfigFile();

        getLog().warn("Checking if the template dir exists" + getTemplateDir());
        if (deleteTemplateIfexist(getTemplateDir())) {
            getLog().warn("Old template directory was found and it was removed!!");
        }

        getLog().warn("Project build dir:" + this.buildDir.getPath());
        getLog().warn("Backstage template dir:" + getTemplateDir());
        getLog().warn("Reading settings from config file!");

        if (this.config.getSettings().get("prefix") == "{{"
                && this.config.getSettings().get("suffix") == "}}") {
            // "Using default placeholder settings!! -> Prefix:{{ and Suffix: }}"
            getLog().warn("Using default placeholder settings!! -> Prefix:"
                    + this.config.getSettings().get("prefix") + " and Suffix: "
                    + this.config.getSettings().get("suffix"));
        } else {
            getLog().warn("Using custom placeholder settings!! -> Prefix:"
                    + this.config.getSettings().get("prefix") + " and Suffix: "
                    + this.config.getSettings().get("suffix"));
        }

        getLog().warn("Starting placheholders");

        // execute each step of the config
        executeSteps();

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
     *
     * Registry pattern for open-closed to set, add, remove, expand the handlers outside of mojo
     * logic withouth changing setPlaceHolders code block.
     */
    private Map<String, FileHandlerKind> getFileHandlerRegistry() {
        return Map.of("xml", this.xmlHandler, "yaml", this.yamlHandler, "yml", this.yamlHandler,
                "json", this.jsonHandler, "java", this.javaHandler);
    }

    private String getTemplateDir() {
        return this.buildDir.getPath() + BUILD_TEMPLATE_DIR;
    }

    private void loadConfigFile() {

        try {
            String configFile = getTemplateDir() + "/maven-cookiecutter.yml";

            getLog().warn("Template file: " + configFile);

            this.config = reader.read(configFile);

            if (this.config.getSettings() == null) {
                Map<String, Object> defaultSettings = new HashMap<>();
                defaultSettings.put("prefix", "{{");
                defaultSettings.put("suffix", "}}");
                this.config.setSettings(defaultSettings);
            }

        } catch (Exception e) {
            getLog().error("Error to read the settings from the config file", e);;
            throw new RuntimeException("Error to read the settings from the config file");
        }
    }


    private void executeSteps() {

        List<StepsKindTemplate> steps = this.config.getSteps();

        for (StepsKindTemplate step : steps) {
            String kind = step.getKind();
            switch (kind) {
                case "FileHandler":
                    processFileHandler(step);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Processes a step of kind FileHandler
     *
     * This method takes a StepsKindTemplate object as a parameter and processes the file handler
     * associated with the given step. The file handler is responsible for performing specific
     * actions related to the step, such as reading or writing data to a file.
     *
     * @param fileHandlerStep The StepsKindTemplate object representing the step for which the file
     *        handler needs to be processed.
     * @throws IllegalArgumentException If the step parameter is null or invalid.
     */
    private void processFileHandler(StepsKindTemplate fileHandlerStep)
            throws IllegalArgumentException {


        // nothing change from v1
        var fileHandlersRegistry = getFileHandlerRegistry();

        var typeVar = fileHandlerStep.getMetadata().getOrDefault("type", "unknown");
        // in v1 was extension, now it's type, ex: xml, yaml
        String type = typeVar.toString();

        // exists handler for this type?
        if (!fileHandlersRegistry.containsKey(type))
            throw new IllegalArgumentException(
                    String.format("Unsupported Kind: FileHandler type: %s", type));

        
        List<Map<String, Object>> specs = fileHandlerStep.getSpecs();

        for(Map<String, Object> spec : specs) {

            List<String> files = (List<String>) spec.getOrDefault("files", new ArrayList<>());
            List<Map<String, String>> placeholders = (List<Map<String, String>>) spec.getOrDefault("placeholders", new ArrayList<>());
        
            for (String file : files) {
                String filePath = getTemplateDir() + "/" + file;
                getLog().warn("Start placeholder for: " + filePath);

                for (Map<String, String> placeholder : placeholders) {
                    String match = placeholder.get("match");
                    String replace = placeholder.get("replace");
                    try {
                        fileHandlersRegistry.get(type).replace(filePath, match,
                                this.config.getSettings().get("prefix") + replace
                                        + this.config.getSettings().get("suffix"));
                    } catch (FileHandlerException e) {
                        e.printStackTrace();
                        getLog().error("Error to replace placeholders", e);
                    }
                }
            }
        }
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
