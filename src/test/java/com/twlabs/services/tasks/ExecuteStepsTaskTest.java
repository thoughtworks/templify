package com.twlabs.services.tasks;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import com.google.common.eventbus.EventBus;
import com.twlabs.config.PluginConfig;
import com.twlabs.kinds.api.KindHandlerEvent;
import com.twlabs.kinds.api.KindMappingTemplate;
import com.twlabs.kinds.api.KindsEventBus;
import com.twlabs.services.CreateTemplateCommand;
import com.twlabs.services.CreateTemplateCommand.CreateTemplateRequestBuilder;
import com.twlabs.services.logger.RunnerLogger;

/**
 * ExecuteStepsTaskTest
 */
public class ExecuteStepsTaskTest {


    private static final String TARGET = "target/";
    private static final String PROJECT_BASIC_CONFIGS =
            "src/test/resources-its/com/twlabs/mojos/CookieCutterMojoIT/configuracao_basica_build_test/";
    private static final String UNSUPPORTED_KIND = "unsupportedKind";
    private static final String XML_HANDLER = "XmlHandler";
    private static final String BUILD_TEMPLATE_DIR = "/template";


    @ParameterizedTest
    @CsvSource(value = {PROJECT_BASIC_CONFIGS})
    public void test_execute_steps(String baseDir, @TempDir Path tmpDir) {

        // Arrange
        EventBus mockEventBus = mock(EventBus.class);
        ExecuteStepsTask executeStepsTask = new ExecuteStepsTask(mockEventBus);

        // Act
        CreateTemplateCommand execute = executeStepsTask
                .execute(TestUtils.createTemplateRequestWithMockedKind(tmpDir, baseDir, XML_HANDLER));

        // Assert
        assertNotNull(execute);
        verify(mockEventBus, times(1))
                .post(argThat(
                        (KindHandlerEvent event) -> event.getKindName().equals(XML_HANDLER)));
    }


    @ParameterizedTest
    @CsvSource(value = {PROJECT_BASIC_CONFIGS})
    public void test_execute_steps_unsuported_kind(String baseDir, @TempDir Path tmpDir) {

        // Arrange
        EventBus spyEventBus = spy(KindsEventBus.getInstance());
        ExecuteStepsTask executeStepsTask =
                new ExecuteStepsTask(spyEventBus);

        // Act
        assertThrows(RuntimeException.class, () -> {
            executeStepsTask.execute(
                    TestUtils.createTemplateRequestWithMockedKind(tmpDir, baseDir, UNSUPPORTED_KIND));
        });

        // Assert
        verify(spyEventBus, times(1))
                .post(argThat(
                        (KindHandlerEvent event) -> event.getKindName().equals(UNSUPPORTED_KIND)));
    }


    // Testing Utils class pattern - provides reusable utils, mock data and methods
    static private class TestUtils {
        static private CreateTemplateCommand createTemplateRequestWithMockedKind(Path dir, String baseDir,
                String kind) {
            RunnerLogger mockLogger = mock(RunnerLogger.class);

            CreateTemplateRequestBuilder requestBuilder = new CreateTemplateRequestBuilder();

            String buildDir = baseDir + TARGET;

            String tempTemplateDir = dir.toFile().getAbsolutePath() + BUILD_TEMPLATE_DIR;

            requestBuilder
                    .withBaseDir(new File(baseDir))
                    .withBuildDir(buildDir)
                    .withConfiguration(TestUtils.createConfigTest(kind))
                    .withTemplateDir(tempTemplateDir)
                    .withLogger(mockLogger);


            return requestBuilder.build();
        }

        static private PluginConfig createConfigTest(String kind) {

            KindMappingTemplate stepsKindTemplate = new KindMappingTemplate();
            stepsKindTemplate.setKind(kind);

            List<KindMappingTemplate> steps = new ArrayList<>();
            steps.add(stepsKindTemplate);

            PluginConfig configuration = new PluginConfig();
            configuration.setSteps(steps);

            return configuration;
        }
    }



}
