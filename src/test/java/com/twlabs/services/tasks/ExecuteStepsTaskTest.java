package com.twlabs.services.tasks;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import com.twlabs.kinds.handlers.filehandler.FileHandlerKindExecutor;
import com.twlabs.model.settings.PluginConfig;
import com.twlabs.kinds.api.KindMappingTemplate;
import com.twlabs.services.CreateTemplateRequest;
import com.twlabs.services.CreateTemplateRequest.CreateTemplateRequestBuilder;
import com.twlabs.services.logger.RunnerLogger;

/**
 * ExecuteStepsTaskTest
 */
public class ExecuteStepsTaskTest {


    private static final String BUILD_TEMPLATE_DIR = "/template";


    @ParameterizedTest
    @CsvSource(value = {
            "src/test/resources-its/com/twlabs/mojos/CookieCutterMojoIT/configuracao_basica_build_test/",
    })
    public void test_execute_steps(String baseDir, @TempDir Path tmpDir) {

        ExecuteStepsTask executeStepsTask =
                new ExecuteStepsTask(mock(FileHandlerKindExecutor.class));

        CreateTemplateRequest execute =
                executeStepsTask.execute(createTemplateRequest(tmpDir, baseDir));

        verify(executeStepsTask.getFileHandlerKind(), Mockito.times(1)).execute(
                Mockito.any(KindMappingTemplate.class),
                Mockito.any(CreateTemplateRequest.class));

        assertNotNull(execute);

    }


    @ParameterizedTest
    @CsvSource(value = {
            "src/test/resources-its/com/twlabs/mojos/CookieCutterMojoIT/configuracao_basica_build_test/",
    })
    public void test_execute_steps_unsuported_kind(String baseDir, @TempDir Path tmpDir) {
        ExecuteStepsTask executeStepsTask =
                new ExecuteStepsTask(Mockito.mock(FileHandlerKindExecutor.class));

        CreateTemplateRequest execute =
                executeStepsTask.execute(createTemplateRequest(tmpDir, baseDir, "unsupported"));


        Mockito.verify(execute.getLogger(), Mockito.times(1)).error(Mockito.anyString());
    }


    private CreateTemplateRequest createTemplateRequest(Path dir, String baseDir, String kind) {
        RunnerLogger mockLogger = Mockito.mock(RunnerLogger.class);

        CreateTemplateRequestBuilder requestBuilder = new CreateTemplateRequestBuilder();

        String buildDir = baseDir + "target/";

        String tempTemplateDir = dir.toFile().getAbsolutePath() + BUILD_TEMPLATE_DIR;

        requestBuilder
                .withBaseDir(new File(baseDir))
                .withBuildDir(buildDir)
                .withConfiguration(this.createConfigTest(kind))
                .withTemplateDir(tempTemplateDir)
                .withLogger(mockLogger);


        return requestBuilder.build();
    }



    private CreateTemplateRequest createTemplateRequest(Path dir, String baseDir) {

        return createTemplateRequest(dir, baseDir, "FileHandler");


    }

    private PluginConfig createConfigTest(String kind) {

        KindMappingTemplate stepsKindTemplate = new KindMappingTemplate();
        stepsKindTemplate.setKind(kind);

        List<KindMappingTemplate> steps = new ArrayList<>();
        steps.add(stepsKindTemplate);

        PluginConfig configuration = new PluginConfig();
        configuration.setSteps(steps);

        return configuration;
    }


}
