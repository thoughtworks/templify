package com.twlabs.mojos;

import com.twlabs.mojos.TemplifyMojo;
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
import com.twlabs.di.CoreModule;
import com.twlabs.services.CreateTemplateCommand.CreateTemplateCommandBuilder;
import com.twlabs.services.CreateTemplateRunner;


@Mojo(name = "templify", defaultPhase = LifecyclePhase.NONE)
public class TemplifyMojo extends AbstractMojo {

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

        getLog().info("Brace yourself! starting Templify maven plugin!!");

        Injector injector = Guice.createInjector(new CoreModule());
        injector.injectMembers(this);


        CreateTemplateCommandBuilder requestBuilder = new CreateTemplateCommandBuilder();

        // request
        requestBuilder
                .withBaseDir(this.baseDir)
                .withBuildDir(this.buildDir.getPath())
                .withTemplateDir(this.buildDir.getPath() + BUILD_TEMPLATE_DIR)
                .withLogger(getLog());

        runner.execute(requestBuilder.build());
    }
}
