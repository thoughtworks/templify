package com.thoughtworks.di;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.thoughtworks.kinds.api.FileHandler;
import com.thoughtworks.kinds.api.Kind;
import com.thoughtworks.kinds.handlers.javahandler.JavaFileHandler;
import com.thoughtworks.kinds.handlers.javahandler.JavaHandlerKind;
import com.thoughtworks.kinds.handlers.jsonhandler.JsonFileHandler;
import com.thoughtworks.kinds.handlers.jsonhandler.JsonHandlerKind;
import com.thoughtworks.kinds.handlers.plaintexthandler.PlainTextHandler;
import com.thoughtworks.kinds.handlers.plaintexthandler.PlainTextHandlerKind;
import com.thoughtworks.kinds.handlers.xmlhandler.XmlFileHandler;
import com.thoughtworks.kinds.handlers.xmlhandler.XmlHandlerKind;
import com.thoughtworks.kinds.handlers.yamlhandler.YamlFileHandler;
import com.thoughtworks.kinds.handlers.yamlhandler.YamlHandlerKind;


/**
 * This Guice module configures all the dependencies needed to process the configuration events
 * (KindHandlerEvents).
 * 
 * The module provides bindings for the following handlers and their corresponding kinds: -
 * JavaFileHandler and JavaHandlerKind - JsonFileHandler and JsonHandlerKind - XmlFileHandler and
 * XmlHandlerKind - YamlFileHandler and YamlHandlerKind
 * 
 * Example usage:
 * 
 * KindHandlersModule kindHandlersModule = new KindHandlersModule(); Injector injector =
 * Guice.createInjector(kindHandlersModule);
 * 
 * // Now the dependencies for processing configuration events are configured and can be used.
 * 
 * @see FileHandler
 * @see JavaFileHandler
 * @see JsonFileHandler
 * @see XmlFileHandler
 * @see YamlFileHandler
 * @see JavaHandlerKind
 * @see JsonHandlerKind
 * @see XmlHandlerKind
 * @see YamlHandlerKind
 */
public class KindHandlersModule extends AbstractModule {


    @Override
    protected void configure() {

        // file Handlers block
        bind(FileHandler.class).annotatedWith(Names.named(FileHandler.Names.JAVA))
                .to(JavaFileHandler.class);
        bind(FileHandler.class).annotatedWith(Names.named(FileHandler.Names.JSON))
                .to(JsonFileHandler.class);
        bind(FileHandler.class).annotatedWith(Names.named(FileHandler.Names.XML))
                .to(XmlFileHandler.class);
        bind(FileHandler.class).annotatedWith(Names.named(FileHandler.Names.YAML))
                .to(YamlFileHandler.class);
        bind(FileHandler.class).annotatedWith(Names.named(FileHandler.Names.YML))
                .to(YamlFileHandler.class);
        bind(FileHandler.class).annotatedWith(Names.named(FileHandler.Names.PLAIN_TEXT))
                .to(PlainTextHandler.class);

        // kind handlers block
        bind(Kind.class).annotatedWith(Names.named(JavaHandlerKind.NAME))
                .to(JavaHandlerKind.class);
        bind(Kind.class).annotatedWith(Names.named(JsonHandlerKind.NAME))
                .to(JsonHandlerKind.class);
        bind(Kind.class).annotatedWith(Names.named(XmlHandlerKind.NAME))
                .to(XmlHandlerKind.class);
        bind(Kind.class).annotatedWith(Names.named(YamlHandlerKind.NAME))
                .to(YamlHandlerKind.class);
        bind(Kind.class).annotatedWith(Names.named(PlainTextHandlerKind.NAME))
                .to(PlainTextHandlerKind.class);
    }

}
