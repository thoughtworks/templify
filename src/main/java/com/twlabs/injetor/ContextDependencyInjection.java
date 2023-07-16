package com.twlabs.injetor;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.twlabs.handlers.JavaHandler;
import com.twlabs.handlers.JsonHandler;
import com.twlabs.handlers.XMLHandler;
import com.twlabs.handlers.YamlHandler;
import com.twlabs.interfaces.ConfigReader;
import com.twlabs.interfaces.FileHandler;
import com.twlabs.kinds.FileHandlerKind;
import com.twlabs.kinds.KindExecutor;
import com.twlabs.services.CreateTemplateRunner;
import com.twlabs.services.RunnerTask;
import com.twlabs.services.RunnerDefault;
import com.twlabs.services.YamlConfigReader;
import com.twlabs.services.tasks.CopyProjectTask;
import com.twlabs.services.tasks.DeleteTemplateIfExistsTask;
import com.twlabs.services.tasks.ExecuteStepsTask;
import com.twlabs.services.tasks.LoadConfigurationTask;
import static com.twlabs.services.RunnerTask.Names.*;
import static com.twlabs.interfaces.FileHandler.Names.*;
import static com.twlabs.kinds.KindExecutor.Names.*;

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
        bind(FileHandler.class).annotatedWith(Names.named(JAVA)).to(JavaHandler.class);
        bind(FileHandler.class).annotatedWith(Names.named(JSON)).to(JsonHandler.class);
        bind(FileHandler.class).annotatedWith(Names.named(XML)).to(XMLHandler.class);
        bind(FileHandler.class).annotatedWith(Names.named(YAML)).to(YamlHandler.class);
        bind(FileHandler.class).annotatedWith(Names.named(YML)).to(YamlHandler.class);

        // general services block and Kinds
        bind(ConfigReader.class).to(YamlConfigReader.class);
        bind(KindExecutor.class).annotatedWith(Names.named(FILE_HANDLER_KIND))
                .to(FileHandlerKind.class);

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


