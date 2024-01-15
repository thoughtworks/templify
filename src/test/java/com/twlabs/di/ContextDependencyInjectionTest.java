package com.twlabs.di;

import static com.twlabs.kinds.api.FileHandler.Names.*;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.stream.Stream;
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
import com.twlabs.kinds.handlers.javahandler.JavaFileHandler;
import com.twlabs.kinds.handlers.jsonhandler.JsonFileHandler;
import com.twlabs.kinds.handlers.xmlhandler.XmlFileHandler;
import com.twlabs.kinds.handlers.yamlhandler.YamlHandler;

/**
 * BasicModuleTest
 */
@DisplayNameGeneration(ReplaceUnderscores.class)
public class ContextDependencyInjectionTest {


    private static final Injector CREATE_INJECTOR =
            Guice.createInjector(new ContextDependencyInjection());

    private static Stream<Arguments> namedFileHandlers() {
        return Stream.of(Arguments.of(JAVA, JavaFileHandler.class),
                Arguments.of(JSON, JsonFileHandler.class), Arguments.of(XML, XmlFileHandler.class),
                Arguments.of(YAML, YamlHandler.class), Arguments.of(YML, YamlHandler.class));
    }

    @ParameterizedTest
    @MethodSource("namedFileHandlers")
    public void test_injector_binds(String name, Class<?> impl) {

        Injector injector = CREATE_INJECTOR;
        FileHandler fileHandler =
                injector.getInstance(Key.get(FileHandler.class, Names.named(name)));

        assertThat(fileHandler).isNotNull().isInstanceOf(impl);
    }
}
