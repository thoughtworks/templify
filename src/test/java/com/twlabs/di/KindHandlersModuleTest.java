package com.twlabs.di;

import static com.twlabs.kinds.api.FileHandler.Names.JAVA;
import static com.twlabs.kinds.api.FileHandler.Names.JSON;
import static com.twlabs.kinds.api.FileHandler.Names.XML;
import static com.twlabs.kinds.api.FileHandler.Names.YAML;
import static com.twlabs.kinds.api.FileHandler.Names.YML;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import com.twlabs.kinds.api.FileHandler;
import com.twlabs.kinds.api.Kind;
import com.twlabs.kinds.api.KindHandler;
import com.twlabs.kinds.handlers.javahandler.JavaFileHandler;
import com.twlabs.kinds.handlers.javahandler.JavaHandlerKind;
import com.twlabs.kinds.handlers.jsonhandler.JsonFileHandler;
import com.twlabs.kinds.handlers.jsonhandler.JsonHandlerKind;
import com.twlabs.kinds.handlers.xmlhandler.XmlFileHandler;
import com.twlabs.kinds.handlers.xmlhandler.XmlHandlerKind;
import com.twlabs.kinds.handlers.yamlhandler.YamlFileHandler;
import com.twlabs.kinds.handlers.yamlhandler.YamlHandlerKind;

/**
 * BasicModuleTest
 */
@DisplayNameGeneration(ReplaceUnderscores.class)
public class KindHandlersModuleTest {


    private Injector injector;

    @BeforeEach
    public void beforeAll() {
        injector = Guice.createInjector(new KindHandlersModule());
    }

    private static Stream<Arguments> namedFileHandlers() {
        return Stream.of(
                Arguments.of(JAVA, JavaFileHandler.class),
                Arguments.of(JSON, JsonFileHandler.class),
                Arguments.of(XML, XmlFileHandler.class),
                Arguments.of(YAML, YamlFileHandler.class),
                Arguments.of(YML, YamlFileHandler.class));
    }

    private static Stream<Arguments> namedKindHandlers() {
        return Stream.of(
                Arguments.of(JavaHandlerKind.NAME, JavaHandlerKind.class),
                Arguments.of(JsonHandlerKind.NAME, JsonHandlerKind.class),
                Arguments.of(XmlHandlerKind.NAME, XmlHandlerKind.class),
                Arguments.of(YamlHandlerKind.NAME, YamlHandlerKind.class));
    }


    @ParameterizedTest
    @MethodSource("namedFileHandlers")
    public void test_injection_named_file_handlers(String name, Class<?> impl) {

        FileHandler fileHandler =
                injector.getInstance(Key.get(FileHandler.class, Names.named(name)));

        assertThat(fileHandler).isNotNull().isInstanceOf(impl);
    }

    @ParameterizedTest
    @MethodSource("namedKindHandlers")
    public void test_injection_kind_handlers(String name, Class<?> impl) {

        Kind<?> kindHandler = injector.getInstance(Key.get(Kind.class, Names.named(name)));

        assertThat(kindHandler).isNotNull().isInstanceOf(impl);
    }

}
