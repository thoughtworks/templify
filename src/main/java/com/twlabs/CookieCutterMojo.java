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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import com.twlabs.exceptions.FileHandlerException;
import com.twlabs.injetor.ContextDependencyInjection;
import com.twlabs.interfaces.ConfigReader;
import com.twlabs.interfaces.FileHandlerKind;
import com.twlabs.model.settings.PlaceholderSettings;
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

    private PlaceholderSettings placeholder;


    public void execute() throws MojoExecutionException, MojoFailureException {

        getLog().info("Brace yourself! starting cookiecutter-templater-maven-plugin!!");

        Injector injector = Guice.createInjector(new ContextDependencyInjection());
        injector.injectMembers(this);

        getLog().warn("Checking if the template dir exists" + getTemplateDir());
        if (deleteTemplateIfexist(getTemplateDir())) {
            getLog().warn("Old template directory was found and it was removed!!");
        }

        // create target/template dir
        copyProjectTo(getTemplateDir());

        // load and validate config file before copy project
        // BUG settings are read from config file in template folder
        loadConfigFile();

        getLog().warn("Project build dir:" + this.buildDir.getPath());
        getLog().warn("Backstage template dir:" + getTemplateDir());
        getLog().warn("Reading settings from config file!");

        if (this.config.getSettings().get("prefix") == "{{"
                && this.config.getSettings().get("suffix") == "}}") {
            // "Using default placeholder settings!! -> Prefix:{{ and Suffix: }}"
        } else {
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

        final ObjectMapper mapper = new ObjectMapper();

        try {
            String configFile = getTemplateDir() + "/maven-cookiecutter.yml";

            getLog().warn("Template file: " + configFile);

            this.config = reader.read(configFile);

            Map<String, String> defaultPlaceholderSettings = new HashMap<>();
            defaultPlaceholderSettings.put("prefix", "{{");
            defaultPlaceholderSettings.put("suffix", "}}");


            if (this.config.getSettings() == null || this.config.getSettings().isEmpty()) {
                Map<String, Object> defaultSettings = new HashMap<>();
                defaultSettings.put("placeholder", defaultPlaceholderSettings);
                this.config.setSettings(defaultSettings);

                getLog().warn("Using default placeholder settings!! -> Prefix:" + "{{"
                        + " and Suffix: " + "}}");

                // TODO strange logic, needs refactory
                this.placeholder = mapper.convertValue(this.config.getSettings().get("placeholder"),
                        PlaceholderSettings.class);

            } else {
                this.placeholder = mapper.convertValue(this.config.getSettings().get("placeholder"),
                        PlaceholderSettings.class);

                getLog().warn("Using custom placeholder settings!! -> Prefix:"
                        + this.placeholder.getPrefix() + " and Suffix: " + placeholder.getSuffix());
            }


        } catch (

        Exception e) {
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


        List<Map<String, Object>> specs = fileHandlerStep.getSpec();

        for (Map<String, Object> spec : specs) {

            // BUG - JavaHandler does not have files
            List<String> files = (List<String>) spec.getOrDefault("files", new ArrayList<>());

            // BUG - When it's not java, it's empty
            String baseDir = String.valueOf(spec.getOrDefault("base_dir", ""));

            List<Map<String, String>> placeholders = (List<Map<String, String>>) spec
                    .getOrDefault("placeholders", new ArrayList<>());

            if ("java".equals(type)) {
                for (Map<String, String> placeholder : placeholders) {
                    String match = placeholder.get("match");
                    String replace = placeholder.get("replace");
                    try {
                        getLog().warn("Replace: " + match + " with: " + this.placeholder.getPrefix()
                                + replace + this.placeholder.getSuffix());

                        fileHandlersRegistry.get(type).replace(getTemplateDir() + "/" +baseDir, match,
                                this.placeholder.getPrefix() + replace
                                        + this.placeholder.getSuffix());
                    } catch (FileHandlerException e) {
                        getLog().error("Error to replace placeholders", e);
                    }
                }
            } else {
                for (String file : files) {
                    String filePath = getTemplateDir() + "/" + file;
                    getLog().warn("Start placeholder for: " + filePath);

                    for (Map<String, String> placeholder : placeholders) {
                        String match = placeholder.get("match");
                        String replace = placeholder.get("replace");
                        try {
                            getLog().warn(
                                    "Replace: " + match + " with: " + this.placeholder.getPrefix()
                                            + replace + this.placeholder.getSuffix());

                            fileHandlersRegistry.get(type).replace(filePath, match,
                                    this.placeholder.getPrefix() + replace
                                            + this.placeholder.getSuffix());
                        } catch (FileHandlerException e) {
                            getLog().error("Error to replace placeholders", e);
                        }
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
