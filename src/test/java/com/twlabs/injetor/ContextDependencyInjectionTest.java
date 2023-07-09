package com.twlabs.injetor;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.stream.Stream;
import org.junit.Test;
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

    private static Stream<Arguments> namedFileHandlers() {
        return Stream.of(Arguments.of("java", JavaHandler.class),
                Arguments.of("json", JsonHandler.class), Arguments.of("xml", XMLHandler.class),
                Arguments.of("yaml", YamlHandler.class), Arguments.of("yml", YamlHandler.class));
    }

    @ParameterizedTest
    @MethodSource("namedFileHandlers")
    public void test_injector_binds(String name, Class<?> impl) {
        Injector injector = Guice.createInjector(new ContextDependencyInjection());
        FileHandler fileHandler =
                injector.getInstance(Key.get(FileHandler.class, Names.named(name)));
        assertThat(fileHandler).isNotNull().isInstanceOf(impl);
    }
}
