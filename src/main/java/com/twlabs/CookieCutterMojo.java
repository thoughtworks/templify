package com.twlabs;

import java.io.File;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.twlabs.injetor.ContextDependencyInjection;
import com.twlabs.services.CreateTemplateRunner;
import com.twlabs.services.RunnerRequest;


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
    private CreateTemplateRunner runner;


    public void execute() throws MojoExecutionException, MojoFailureException {

        getLog().info("Brace yourself! starting cookiecutter-templater-maven-plugin!!");

        Injector injector = Guice.createInjector(new ContextDependencyInjection());
        injector.injectMembers(this);

        RunnerRequest request = new RunnerRequest();
        request.setBaseDir(this.baseDir);
        request.setBuildDir(this.buildDir.getPath());
        request.setTemplateDir(this.buildDir.getPath() + BUILD_TEMPLATE_DIR);
        request.setLogger(getLog());

        runner.execute(request);

        getLog().warn("Project build dir:" + this.buildDir.getPath());
        getLog().warn("Backstage template dir:" + getTemplateDir());
        getLog().warn("Reading settings from config file!");
        getLog().warn("Starting placheholders");

        getLog().warn("End to config placeholders");
    }



    private String getTemplateDir() {
        return this.buildDir.getPath() + BUILD_TEMPLATE_DIR;
    }
}
