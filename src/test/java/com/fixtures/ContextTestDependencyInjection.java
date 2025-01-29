package com.fixtures;

import static com.thoughtworks.kinds.api.FileHandler.Names.JAVA;
import static com.thoughtworks.kinds.api.FileHandler.Names.JSON;
import static com.thoughtworks.kinds.api.FileHandler.Names.XML;
import static com.thoughtworks.kinds.api.FileHandler.Names.YAML;
import static com.thoughtworks.kinds.api.FileHandler.Names.YML;
import static com.thoughtworks.services.RunnerTask.Names.COPY_PROJECT_TASK;
import static com.thoughtworks.services.RunnerTask.Names.DELETE_TEMPLATE_FOLDER_TASK;
import static com.thoughtworks.services.RunnerTask.Names.EXECUTE_STEPS_TASK;
import static com.thoughtworks.services.RunnerTask.Names.LOAD_PLUGIN_CONFIGURATION_TASK;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.thoughtworks.kinds.api.FileHandler;
import com.thoughtworks.kinds.handlers.javahandler.JavaFileHandler;
import com.thoughtworks.kinds.handlers.jsonhandler.JsonFileHandler;
import com.thoughtworks.kinds.handlers.xmlhandler.XmlFileHandler;
import com.thoughtworks.kinds.handlers.yamlhandler.YamlFileHandler;
import com.thoughtworks.config.ConfigReader;
import com.thoughtworks.services.CreateTemplateRunner;
import com.thoughtworks.services.RunnerDefault;
import com.thoughtworks.services.RunnerTask;
import com.thoughtworks.services.YamlConfigReader;
import com.thoughtworks.services.tasks.CopyProjectTask;
import com.thoughtworks.services.tasks.DeleteTemplateIfExistsTask;
import com.thoughtworks.services.tasks.ExecuteStepsTask;
import com.thoughtworks.services.tasks.LoadConfigurationTask;

/**
 * ContextTestDependencyInjection
 */
public class ContextTestDependencyInjection extends AbstractModule {

    @Override
    protected void configure() {

        // main logic entrypoint
        bind(CreateTemplateRunner.class).to(RunnerDefault.class);

        // file Handlers block
        bind(FileHandler.class).annotatedWith(Names.named(JAVA))
                .toInstance(mock(JavaFileHandler.class));
        bind(FileHandler.class).annotatedWith(Names.named(JSON))
                .toInstance(spy(JsonFileHandler.class));
        bind(FileHandler.class).annotatedWith(Names.named(XML))
                .toInstance(spy(XmlFileHandler.class));
        bind(FileHandler.class).annotatedWith(Names.named(YAML))
                .toInstance(spy(YamlFileHandler.class));
        bind(FileHandler.class).annotatedWith(Names.named(YML))
                .toInstance(spy(YamlFileHandler.class));

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
                .to(ExecuteStepsTask.class);
    }
}
