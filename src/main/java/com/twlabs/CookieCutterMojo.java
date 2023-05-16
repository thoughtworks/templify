package com.twlabs;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.FileUtils;
import com.twlabs.exceptions.FileHandlerException;
import com.twlabs.handlers.XMLHandler;
import com.twlabs.handlers.YamlHandler;
import com.twlabs.handlers.JavaHandler;
import com.twlabs.handlers.JsonHandler;
import com.twlabs.interfaces.ConfigReader;
import com.twlabs.interfaces.FileHandler;
import com.twlabs.model.Mapping;
import com.twlabs.model.Placeholder;
import com.twlabs.model.PluginConfig;
import com.twlabs.services.YamlReader;


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

    private ConfigReader reader = new YamlReader();

    public void execute() throws MojoExecutionException, MojoFailureException {

        getLog().info("Brace yourself! starting cookiecutter-templater-maven-plugin!!");


        copyProjectTo(getTemplateDir());

        getLog().warn("Project build dir:" + this.buildDir.getPath());
        getLog().warn("Backstage template dir:" + getTemplateDir());

        getLog().warn("Starting placheholders");
        setPlaceHolders();
        getLog().warn("End to config placeholders");

    }

    /**
     *
     * Registry pattern for open-closed to set, add, remove, expand the handlers outside of mojo
     * logic withouth changing setPlaceHolders code block.
     */
    private Map<String, FileHandler> getFileHandlerRegistry() {
        return Map.of("xml", this.xmlHandler, "yaml", this.yamlHandler, "yml", this.yamlHandler,
                "json", this.jsonHandler, "java", this.javaHandler);
    }

    private String getTemplateDir() {
        return this.buildDir.getPath() + BUILD_TEMPLATE_DIR;
    }

    private PluginConfig getConfig() throws IOException {

        String configFile = getTemplateDir() + "/template.yml";

        getLog().warn("Template file: " + configFile);

        PluginConfig config = reader.read(configFile);

        return config;
    }


    private void setPlaceHolders() {

        try {
            getConfig().getMappings().forEach(this::setPlaceHolder);

        } catch (IOException e) {
            getLog().error("Error to read the config file", e);;
        }
    }

    private void setPlaceHolder(Mapping mapping) {

        Map<String, FileHandler> handlersRegistry = getFileHandlerRegistry();

        String file = mapping.getFile();
        String filePath = getTemplateDir() + "/" + file;
        String extension = getFileExtension(mapping);

        if (!handlersRegistry.containsKey(extension))
            throw new IllegalArgumentException("Unsupported file type: " + file);

        getLog().warn("Start placeholder for: " + filePath);

        for (Placeholder placeholder : mapping.getPlaceholders()) {
            try {
                handlersRegistry.get(extension).replace(filePath, placeholder.getQuery(),
                        "{{" + placeholder.getName() + "}}");
            } catch (FileHandlerException e) {
                e.printStackTrace();
                getLog().error("Error to replace placeholders", e);
            }
        }
    }


    private String getFileExtension(Mapping mapping) {
        int lastDotIndex = mapping.getFile().lastIndexOf(".");
        if (lastDotIndex == -1) {
            return mapping.getType();
        }
        return mapping.getFile().substring(lastDotIndex).replace(".", "");
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
