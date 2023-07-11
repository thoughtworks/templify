package com.twlabs.injetor;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.twlabs.handlers.JavaHandler;
import com.twlabs.handlers.JsonHandler;
import com.twlabs.handlers.XMLHandler;
import com.twlabs.handlers.YamlHandler;
import com.twlabs.interfaces.ConfigReader;
import com.twlabs.interfaces.FileHandlerKind;
import com.twlabs.services.YamlConfigReader;

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

        // file Handlers block
        bind(FileHandlerKind.class).annotatedWith(Names.named("java")).to(JavaHandler.class);
        bind(FileHandlerKind.class).annotatedWith(Names.named("json")).to(JsonHandler.class);
        bind(FileHandlerKind.class).annotatedWith(Names.named("xml")).to(XMLHandler.class);
        bind(FileHandlerKind.class).annotatedWith(Names.named("yaml")).to(YamlHandler.class);
        bind(FileHandlerKind.class).annotatedWith(Names.named("yml")).to(YamlHandler.class);

        // services block
        bind(ConfigReader.class).to(YamlConfigReader.class);
    }
}


