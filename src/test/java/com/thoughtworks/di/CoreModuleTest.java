package com.thoughtworks.di;

import static com.thoughtworks.services.RunnerTask.Names.COPY_PROJECT_TASK;
import static com.thoughtworks.services.RunnerTask.Names.DELETE_TEMPLATE_FOLDER_TASK;
import static com.thoughtworks.services.RunnerTask.Names.EXECUTE_STEPS_TASK;
import static com.thoughtworks.services.RunnerTask.Names.LOAD_PLUGIN_CONFIGURATION_TASK;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.EventBusWithExceptionHandler;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
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
 * BasicModuleTest
 */
@DisplayNameGeneration(ReplaceUnderscores.class)
public class CoreModuleTest {


    private Injector injector;

    @BeforeEach
    public void beforeAll() {
        injector = Guice.createInjector(new CoreModule());
    }


    private static Stream<Arguments> namedRunnerTasks() {
        return Stream.of(
                Arguments.of(COPY_PROJECT_TASK, CopyProjectTask.class),
                Arguments.of(DELETE_TEMPLATE_FOLDER_TASK, DeleteTemplateIfExistsTask.class),
                Arguments.of(EXECUTE_STEPS_TASK, ExecuteStepsTask.class),
                Arguments.of(LOAD_PLUGIN_CONFIGURATION_TASK, LoadConfigurationTask.class));
    }

    private static Stream<Arguments> generalBindsSingletons() {
        return Stream.of(
                Arguments.of(EventBus.class, EventBusWithExceptionHandler.class));
    }

    private static Stream<Arguments> generalBinds() {
        return Stream.of(
                Arguments.of(ConfigReader.class, YamlConfigReader.class),
                Arguments.of(CreateTemplateRunner.class, RunnerDefault.class));
    }

    @ParameterizedTest
    @MethodSource("namedRunnerTasks")
    public void test_injection_named_runner_tasks(String name, Class<?> impl) {

        RunnerTask task =
                injector.getInstance(Key.get(RunnerTask.class, Names.named(name)));

        assertThat(task).isNotNull().isInstanceOf(impl);
    }

    @ParameterizedTest
    @MethodSource("generalBinds")
    public void test_injection_of_commons_binds(Class<?> type, Class<?> impl) {

        Object instance = injector.getInstance(Key.get(type));
        assertThat(instance).isNotNull().isExactlyInstanceOf(impl);
    }

    @ParameterizedTest
    @MethodSource("generalBindsSingletons")
    public void test_injection_of_commons_binds_singletons(Class<?> type, Class<?> impl) {

        Object instance = injector.getInstance(Key.get(type));
        assertThat(instance).isNotNull().isExactlyInstanceOf(impl);

        Object instance2 = injector.getInstance(Key.get(type));
        assertThat(instance).isNotNull().isExactlyInstanceOf(impl);

        assertThat(instance).isSameAs(instance2);
    }
}
