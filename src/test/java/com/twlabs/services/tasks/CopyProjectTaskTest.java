package com.twlabs.services.tasks;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import com.twlabs.services.CreateTemplateRequest;
import com.twlabs.services.CreateTemplateRequest.CreateTemplateRequestBuilder;

/**
 * CopyProjectTaskTest
 */
public class CopyProjectTaskTest {

    private static final String BUILD_TEMPLATE_DIR = "/template";

    @Test
    public void test_copy_project_task(@TempDir Path tempDir) {

        String baseDir =
                "src/test/resources-its/com/twlabs/mojos/CookieCutterMojoIT/configuracao_basica_build_test/";

        CreateTemplateRequestBuilder requestBuilder = new CreateTemplateRequestBuilder();

        CopyProjectTask task = new CopyProjectTask();

        String buildDir = baseDir + "target/";

        String tempTemplateDir = tempDir.toFile().getAbsolutePath() + BUILD_TEMPLATE_DIR;

        requestBuilder
                .withBaseDir(new File(baseDir))
                .withBuildDir(buildDir)
                .withTemplateDir(tempTemplateDir);

        CreateTemplateRequest execute = task.execute(requestBuilder.build());

        assertTrue(new File(tempTemplateDir).exists());
        assertTrue(new File(tempTemplateDir + "/pom.xml").exists());
        assertTrue(new File(tempTemplateDir).isDirectory());
        assertNotNull(execute);


    }
}
