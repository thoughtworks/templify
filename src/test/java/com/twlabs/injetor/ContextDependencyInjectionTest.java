package com.twlabs.injetor;

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
import com.twlabs.handlers.JavaHandler;
import com.twlabs.handlers.JsonHandler;
import com.twlabs.handlers.XMLHandler;
import com.twlabs.handlers.YamlHandler;
import com.twlabs.interfaces.FileHandler;

/**
 * BasicModuleTest
 */
@DisplayNameGeneration(ReplaceUnderscores.class)
public class ContextDependencyInjectionTest {

    private static final String YML = "yml";
    private static final String YAML = "yaml";
    private static final String XML = "xml";
    private static final String JSON = "json";
    private static final String JAVA = "java";

    private static final Injector CREATE_INJECTOR =
            Guice.createInjector(new ContextDependencyInjection());

    private static Stream<Arguments> namedFileHandlers() {
        return Stream.of(Arguments.of(JAVA, JavaHandler.class),
                Arguments.of(JSON, JsonHandler.class), Arguments.of(XML, XMLHandler.class),
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
