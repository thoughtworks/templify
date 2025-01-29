package com.thoughtworks.di;

import static com.thoughtworks.services.RunnerTask.Names.COPY_PROJECT_TASK;
import static com.thoughtworks.services.RunnerTask.Names.DELETE_TEMPLATE_FOLDER_TASK;
import static com.thoughtworks.services.RunnerTask.Names.EXECUTE_STEPS_TASK;
import static com.thoughtworks.services.RunnerTask.Names.LOAD_PLUGIN_CONFIGURATION_TASK;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.EventBusWithExceptionHandler;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
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
 * This Guice module configures all components needed for the core module of the plugin: Event Bus,
 * CreateTemplateRunner, ConfigReader, and RunnerTasks.
 * 
 * The CoreModule class extends the AbstractModule class, which is a part of the Guice framework.
 * Guice is a lightweight dependency injection framework for Java. It allows for the decoupling of
 * dependencies and promotes modular design.
 * 
 * The CoreModule class is responsible for configuring and binding the necessary components for the
 * core functionality of the plugin. These components include the Event Bus, CreateTemplateRunner,
 * ConfigReader, and RunnerTasks.
 * 
 * The Event Bus is a publish-subscribe event system that allows different components of the plugin
 * to communicate with each other. It provides a way for publish configurations events to
 * kindHandlersModule consume.
 * 
 * The CreateTemplateRunner is a class that handles the creation of templates for the plugin. It
 * provides methods for generating template files based on user input and configuration settings.
 * It's a facade to maven mojos and future CLI support.
 * 
 * The ConfigReader is responsible for reading and parsing the configuration file for the plugin. It
 * provides methods for retrieving configuration settings and values.
 * 
 * The RunnerTasks class is a collection of tasks that can be executed by the plugin. It provides
 * methods for running tasks and managing their execution. ex: CopyProject, Delete build directory,
 * Load Configurations, etc.
 * 
 * This class serves as the entry point for configuring the core module of the plugin. It is
 * responsible for binding the necessary components and setting up any required dependencies.
 * 
 * Example usage:
 * 
 * CoreModule coreModule = new CoreModule(); Injector injector = Guice.createInjector(coreModule);
 * 
 * The CoreModule class should be instantiated and passed to the Guice.createInjector() method to
 * create an instance of the injector. The injector can then be used to retrieve instances of the
 * configured components.
 * 
 * @see AbstractModule
 * @see EventBus
 * @see CreateTemplateRunner
 * @see ConfigReader
 * @see RunnerTasks
 */
public class CoreModule extends AbstractModule {


    @Override
    protected void configure() {

        // event bus
        bind(EventBus.class).to(EventBusWithExceptionHandler.class).in(Singleton.class);

        // create template runner
        bind(CreateTemplateRunner.class).to(RunnerDefault.class);

        // config reader
        bind(ConfigReader.class).to(YamlConfigReader.class);

        // tasks
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
