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
import com.twlabs.services.CreateTemplateRequest.CreateTemplateRequestBuilder;
import com.twlabs.services.CreateTemplateRequest;


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


        CreateTemplateRequestBuilder requestBuilder = new CreateTemplateRequestBuilder();
        requestBuilder.withBaseDir(this.baseDir).withBuildDir(this.buildDir.getPath())
                .withTemplateDir(this.buildDir.getPath() + BUILD_TEMPLATE_DIR).withLogger(getLog());

        runner.execute(requestBuilder.build());

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
