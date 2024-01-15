package com.twlabs.di;

import static com.twlabs.kinds.api.FileHandler.Names.JAVA;
import static com.twlabs.kinds.api.FileHandler.Names.JSON;
import static com.twlabs.kinds.api.FileHandler.Names.XML;
import static com.twlabs.kinds.api.FileHandler.Names.YAML;
import static com.twlabs.kinds.api.FileHandler.Names.YML;
import static com.twlabs.services.RunnerTask.Names.COPY_PROJECT_TASK;
import static com.twlabs.services.RunnerTask.Names.DELETE_TEMPLATE_FOLDER_TASK;
import static com.twlabs.services.RunnerTask.Names.EXECUTE_STEPS_TASK;
import static com.twlabs.services.RunnerTask.Names.LOAD_PLUGIN_CONFIGURATION_TASK;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.twlabs.kinds.api.FileHandler;
import com.twlabs.kinds.api.KindsEventBus;
import com.twlabs.kinds.handlers.javahandler.JavaFileHandler;
import com.twlabs.kinds.handlers.jsonhandler.JsonFileHandler;
import com.twlabs.kinds.handlers.xmlhandler.XmlFileHandler;
import com.twlabs.kinds.handlers.yamlhandler.YamlFileHandler;
import com.twlabs.config.ConfigReader;
import com.twlabs.services.CreateTemplateRunner;
import com.twlabs.services.RunnerDefault;
import com.twlabs.services.RunnerTask;
import com.twlabs.services.YamlConfigReader;
import com.twlabs.services.tasks.CopyProjectTask;
import com.twlabs.services.tasks.DeleteTemplateIfExistsTask;
import com.twlabs.services.tasks.ExecuteStepsTask;
import com.twlabs.services.tasks.LoadConfigurationTask;

/**
 * This class represents a module for configuring context dependency injection. It extends the
 * AbstractModule class and overrides the configure() method.
 * 
 * The configure() method is responsible for binding different implementations of the FileHandler
 * interface to their corresponding annotations. This allows for dependency injection based on the
 * annotation used.
 * 
 * This module can be used in conjunction with a dependency injection framework, such as Guice, to
 * provide the appropriate implementation of interface based on the annotation used or other
 * customization.
 * 
 * Example usage:
 * 
 * <pre>
 * ContextDependencyInjection context = new ContextDependencyInjection(); Injector injector =
 * Guice.createInjector(context);
 * 
 * FileHandler javaHandler = injector.getInstance(Key.get(FileHandler.class, Names.named("java")));
 * FileHandler jsonHandler = injector.getInstance(Key.get(FileHandler.class, Names.named("json")));
 * FileHandler xmlHandler = injector.getInstance(Key.get(FileHandler.class, Names.named("xml")));
 * 
 * <pre>
 */
public class ContextDependencyInjection extends AbstractModule {

    @Override
    protected void configure() {

        // main logic entrypoint
        bind(CreateTemplateRunner.class).to(RunnerDefault.class);

        // file Handlers block
        bind(FileHandler.class).annotatedWith(Names.named(JAVA)).to(JavaFileHandler.class);
        bind(FileHandler.class).annotatedWith(Names.named(JSON)).to(JsonFileHandler.class);
        bind(FileHandler.class).annotatedWith(Names.named(XML)).to(XmlFileHandler.class);
        bind(FileHandler.class).annotatedWith(Names.named(YAML)).to(YamlFileHandler.class);
        bind(FileHandler.class).annotatedWith(Names.named(YML)).to(YamlFileHandler.class);

        // general services block and Kinds
        bind(ConfigReader.class).to(YamlConfigReader.class);

        // runner tasks block
        bind(RunnerTask.class).annotatedWith(Names.named(COPY_PROJECT_TASK))
                .to(CopyProjectTask.class);

        bind(RunnerTask.class).annotatedWith(Names.named(DELETE_TEMPLATE_FOLDER_TASK))
                .to(DeleteTemplateIfExistsTask.class);

        bind(RunnerTask.class).annotatedWith(Names.named(LOAD_PLUGIN_CONFIGURATION_TASK))
                .to(LoadConfigurationTask.class);

        bind(RunnerTask.class).annotatedWith(Names.named(EXECUTE_STEPS_TASK))
                .toInstance(new ExecuteStepsTask(KindsEventBus.getInstance()));
    }
}
