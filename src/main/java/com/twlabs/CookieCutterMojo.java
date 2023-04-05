package com.twlabs;

import java.util.List;
import org.apache.maven.model.Dependency;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */


import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

/**
 * Goal which touches a timestamp file.
 *
 * @goal cutter-plugin
 * 
 * @phase process-sources
 */
@Mojo(name = "micci", defaultPhase = LifecyclePhase.NONE)
public class CookieCutterMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    MavenProject project;


    public void execute() throws MojoExecutionException, MojoFailureException {

        // Um log para sabermos que o plugin está sendo iniciado e para validar os testes
        // integrados
        getLog().info("Brace yourself! Iniciando o cookiecutter-templater-maven-plugin!!");

        List<Dependency> dependencias = project.getDependencies();

        long numDependencias = dependencias.stream().count();

        getLog().info("Olá Mundo! Esse projeto possui " + numDependencias + " dependencias");



    }


}
