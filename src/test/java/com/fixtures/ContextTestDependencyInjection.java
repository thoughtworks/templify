package com.fixtures;

import static com.twlabs.interfaces.FileHandler.Names.JAVA;
import static com.twlabs.interfaces.FileHandler.Names.JSON;
import static com.twlabs.interfaces.FileHandler.Names.XML;
import static com.twlabs.interfaces.FileHandler.Names.YAML;
import static com.twlabs.interfaces.FileHandler.Names.YML;
import static com.twlabs.services.RunnerTask.Names.COPY_PROJECT_TASK;
import static com.twlabs.services.RunnerTask.Names.DELETE_TEMPLATE_FOLDER_TASK;
import static com.twlabs.services.RunnerTask.Names.EXECUTE_STEPS_TASK;
import static com.twlabs.services.RunnerTask.Names.LOAD_PLUGIN_CONFIGURATION_TASK;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.twlabs.handlers.JavaHandler;
import com.twlabs.handlers.JsonHandler;
import com.twlabs.handlers.XMLHandler;
import com.twlabs.handlers.YamlHandler;
import com.twlabs.interfaces.ConfigReader;
import com.twlabs.interfaces.FileHandler;
import com.twlabs.kinds.filehandler.FileHandlerKindExecutor;
import com.twlabs.services.CreateTemplateRunner;
import com.twlabs.services.RunnerDefault;
import com.twlabs.services.RunnerTask;
import com.twlabs.services.YamlConfigReader;
import com.twlabs.services.tasks.CopyProjectTask;
import com.twlabs.services.tasks.DeleteTemplateIfExistsTask;
import com.twlabs.services.tasks.ExecuteStepsTask;
import com.twlabs.services.tasks.LoadConfigurationTask;

/**
 * ContextTestDependencyInjection
 */
public class ContextTestDependencyInjection extends AbstractModule {

    @Override
    protected void configure() {

        bind(FileHandlerKindExecutor.class).toInstance(new FileHandlerKindExecutor());

        // main logic entrypoint
        bind(CreateTemplateRunner.class).to(RunnerDefault.class);

        // file Handlers block
        bind(FileHandler.class).annotatedWith(Names.named(JAVA))
                .toInstance(mock(JavaHandler.class));
        bind(FileHandler.class).annotatedWith(Names.named(JSON))
                .toInstance(spy(JsonHandler.class));
        bind(FileHandler.class).annotatedWith(Names.named(XML))
                .toInstance(spy(XMLHandler.class));
        bind(FileHandler.class).annotatedWith(Names.named(YAML))
                .toInstance(spy(YamlHandler.class));
        bind(FileHandler.class).annotatedWith(Names.named(YML))
                .toInstance(spy(YamlHandler.class));

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
