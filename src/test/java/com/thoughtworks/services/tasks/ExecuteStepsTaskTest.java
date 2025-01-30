package com.thoughtworks.services.tasks;

import static com.thoughtworks.services.RunnerTask.Names.EXECUTE_STEPS_TASK;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.EventBusWithExceptionHandler;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import com.thoughtworks.config.PluginConfig;
import com.thoughtworks.kinds.api.KindHandlerEvent;
import com.thoughtworks.kinds.api.KindMappingTemplate;
import com.thoughtworks.services.CreateTemplateCommand;
import com.thoughtworks.services.CreateTemplateCommand.CreateTemplateCommandBuilder;
import com.thoughtworks.services.RunnerTask;
import com.thoughtworks.services.logger.RunnerLogger;

/**
 * ExecuteStepsTaskTest
 */
public class ExecuteStepsTaskTest {


    private static final String TARGET = "target/";
    private static final String PROJECT_BASIC_CONFIGS =
            "src/test/resources-its/com/thoughtworks/mojos/TemplifyIT/basic_usage_example/";
    private static final String UNSUPPORTED_KIND = "unsupportedKind";
    private static final String XML_HANDLER = "XmlHandler";
    private static final String BUILD_TEMPLATE_DIR = "/template";

    @Inject
    @Named(EXECUTE_STEPS_TASK)
    RunnerTask spyExecuteStepsTask;

    @Inject
    EventBus spyEventBus;

    class ModuleTest extends AbstractModule {

        @Override
        protected void configure() {
            bind(EventBus.class).toInstance(spy(EventBusWithExceptionHandler.class));
            bind(RunnerTask.class).annotatedWith(Names.named(EXECUTE_STEPS_TASK))
                    .to(ExecuteStepsTask.class);

        }

    }

    @BeforeEach
    public void setup() {
        Injector injector = Guice.createInjector(new ModuleTest());
        injector.injectMembers(this);
        spyExecuteStepsTask = spy(spyExecuteStepsTask);
    }

    @ParameterizedTest
    @CsvSource(value = {PROJECT_BASIC_CONFIGS})
    public void test_execute_steps(String baseDir, @TempDir Path tmpDir) {

        // ARRANGE:
        doNothing().when(spyEventBus).post(any());
        CreateTemplateCommand spyCommand =
                TestUtils.createSpyTemplateRequestWithMockedKind(tmpDir, baseDir,
                        XML_HANDLER);

        // ACT:
        CreateTemplateCommand execute = spyExecuteStepsTask.execute(spyCommand);

        // ASSERT:
        assertNotNull(execute);
        verify(execute.getLogger(), times(1)).info(contains("Producing KindHandlerEvent:"));
        verify(spyEventBus, times(1))
                .post(argThat(
                        (KindHandlerEvent event) -> event.getKindName().equals(XML_HANDLER)));
    }


    @ParameterizedTest
    @CsvSource(value = {PROJECT_BASIC_CONFIGS})
    public void test_execute_steps_unsuported_kind(String baseDir, @TempDir Path tmpDir) {

        // ACT:
        assertThrows(RuntimeException.class, () -> {
            spyExecuteStepsTask.execute(
                    TestUtils.createSpyTemplateRequestWithMockedKind(tmpDir, baseDir,
                            UNSUPPORTED_KIND));
        });

        // ASSERT:
        verify(spyEventBus, times(1))
                .post(argThat(
                        (KindHandlerEvent event) -> event.getKindName().equals(UNSUPPORTED_KIND)));
    }


    // Testing Utils class pattern - provides reusable utils, mock data and methods
    static private class TestUtils {
        static private CreateTemplateCommand createSpyTemplateRequestWithMockedKind(Path dir,
                String baseDir,
                String kind) {
            RunnerLogger mockLogger = mock(RunnerLogger.class);

            CreateTemplateCommandBuilder requestBuilder = new CreateTemplateCommandBuilder();

            String buildDir = baseDir + TARGET;

            String tempTemplateDir = dir.toFile().getAbsolutePath() + BUILD_TEMPLATE_DIR;

            requestBuilder
                    .withBaseDir(new File(baseDir))
                    .withBuildDir(buildDir)
                    .withConfiguration(TestUtils.createSpyConfigTest(kind))
                    .withTemplateDir(tempTemplateDir)
                    .withLogger(mockLogger);


            return spy(requestBuilder.build());
        }

        static private PluginConfig createSpyConfigTest(String kind) {

            KindMappingTemplate stepsKindTemplate = new KindMappingTemplate();
            stepsKindTemplate.setKind(kind);

            List<KindMappingTemplate> steps = new ArrayList<>();
            steps.add(stepsKindTemplate);

            PluginConfig configuration = new PluginConfig();
            configuration.setSteps(steps);

            return spy(configuration);
        }
    }



}
