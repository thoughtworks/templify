package com.twlabs.services.tasks;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;
import com.twlabs.kinds.filehandler.FileHandlerKindExecutor;
import com.twlabs.model.settings.PluginConfig;
import com.twlabs.model.settings.StepsKindTemplate;
import com.twlabs.services.CreateTemplateRequest;
import com.twlabs.services.CreateTemplateRequest.CreateTemplateRequestBuilder;

/**
 * ExecuteStepsTaskTest
 */
public class ExecuteStepsTaskTest {


    private static final String BUILD_TEMPLATE_DIR = "/template";

    @Test
    public void test_execute_steps(@TempDir Path tmpDir) {

        ExecuteStepsTask executeStepsTask = new ExecuteStepsTask(mock(FileHandlerKindExecutor.class));

        CreateTemplateRequest execute = executeStepsTask.execute(createTemplateRequest(tmpDir));

        verify(executeStepsTask.getFileHandlerKind(), Mockito.times(1)).execute(
                Mockito.any(StepsKindTemplate.class),
                Mockito.any(CreateTemplateRequest.class));

        assertNotNull(execute);

    }

    private CreateTemplateRequest createTemplateRequest(Path dir) {

        String baseDir =
                "src/test/resources-its/com/twlabs/mojos/CookieCutterMojoIT/configuracao_basica_build_test/";

        CreateTemplateRequestBuilder requestBuilder = new CreateTemplateRequestBuilder();

        String buildDir = baseDir + "target/";

        String tempTemplateDir = dir.toFile().getAbsolutePath() + BUILD_TEMPLATE_DIR;

        requestBuilder
                .withBaseDir(new File(baseDir))
                .withBuildDir(buildDir)
                .withConfiguration(this.createConfigTest())
                .withTemplateDir(tempTemplateDir);

        return requestBuilder.build();
    }

    private PluginConfig createConfigTest() {

        StepsKindTemplate stepsKindTemplate = new StepsKindTemplate();
        stepsKindTemplate.setKind("FileHandler");

        List<StepsKindTemplate> steps = new ArrayList<>();
        steps.add(stepsKindTemplate);

        PluginConfig configuration = new PluginConfig();
        configuration.setSteps(steps);

        return configuration;
    }


}
