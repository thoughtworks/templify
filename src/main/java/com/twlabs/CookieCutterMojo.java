package com.twlabs;

import java.io.File;
import java.io.IOException;
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

    public void execute() throws MojoExecutionException, MojoFailureException {

        getLog().info("Brace yourself! starting cookiecutter-templater-maven-plugin!!");

        String templateDir = this.buildDir.getPath() + BUILD_TEMPLATE_DIR;

        copyProjectTo(templateDir);

        getLog().warn("Project build dir:" + this.buildDir.getPath());
        getLog().warn("Backstage template dir:" + templateDir);

        getLog().warn("Starting placheholders");
        setPlaceHolders(templateDir);
        getLog().warn("End to config placeholders");

    }


    private void setPlaceHolders(String templateDir) {

        String configFile = templateDir + "/template.yml";
        ConfigReader reader = new YamlReader();
        PluginConfig config;
        getLog().warn("Template file: " + configFile);
        try {
            config = reader.read(configFile);

            for (Mapping mapping : config.getMappings()) {

                switch (getFileExtension(mapping.getFile())) {
                    case ".xml":
                        setPlaceHolder(mapping, templateDir, new XMLHandler());
                        break;

                    case ".yml":
                        setPlaceHolder(mapping, templateDir, new YamlHandler());
                        break;
                    default:
                        getLog().info("Unsupported file type: " + mapping.getFile());
                        throw new IllegalArgumentException(
                                "Unsupported file type: " + mapping.getFile());
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
            getLog().error("Error when I try to read the config file", e);;
        }
    }



    private void setPlaceHolder(Mapping mapping, String templateDir, FileHandler fileHandler) {
        String filePath = templateDir + "/" + mapping.getFile();
        getLog().warn("Start placeholder for: " + filePath);
        for (Placeholder placeholder : mapping.getPlaceholders()) {
            try {
                fileHandler.replace(filePath, placeholder.getQuery(),
                        "{{" + placeholder.getName() + "}}");
            } catch (FileHandlerException e) {
                e.printStackTrace();
                getLog().error("Error while I was doing some placeholders", e);
            }
        }
    }


    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex == -1) {
            return "";
        }
        return fileName.substring(lastDotIndex);
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
