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

    }

    /**
     * Copy base dir to the dest folder to the replaces
     * 
     * @throws MojoExecutionException
     */
    private void copyProjectTo(String dest) throws MojoExecutionException {
        try {

            FileUtils.copyDirectory(this.baseDir, new File(dest));

        } catch (IOException e) {
            throw new MojoExecutionException(
                    "Something went wrong while copying the project to the template folder.", e);
        }
    }
}
