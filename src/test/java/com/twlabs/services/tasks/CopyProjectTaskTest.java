package com.twlabs.services.tasks;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;
import com.twlabs.services.CreateTemplateCommand;
import com.twlabs.services.CreateTemplateCommand.CreateTemplateCommandBuilder;
import com.twlabs.services.fs.FileSystem;
import com.twlabs.services.logger.RunnerLogger;

/**
 * CopyProjectTaskTest
 */
public class CopyProjectTaskTest {

    private static final String BUILD_TEMPLATE_DIR = "/template";

    @Test
    public void test_copy_project_task(@TempDir Path tempDir) {


        RunnerLogger mockLogger = Mockito.mock(RunnerLogger.class);

        String baseDir =
                "src/test/resources-its/com/twlabs/mojos/CookieCutterMojoIT/test_basic_usage_example/";

        CreateTemplateCommandBuilder requestBuilder = new CreateTemplateCommandBuilder();

        CopyProjectTask task = new CopyProjectTask();

        String buildDir = baseDir + "target/";

        String tempTemplateDir = tempDir.toFile().getAbsolutePath() + BUILD_TEMPLATE_DIR;

        requestBuilder.withBaseDir(new File(baseDir)).withBuildDir(buildDir)
                .withTemplateDir(tempTemplateDir).withLogger(mockLogger);

        CreateTemplateCommand execute = task.execute(requestBuilder.build());

        assertTrue(new File(tempTemplateDir).exists());
        assertTrue(new File(tempTemplateDir + "/pom.xml").exists());
        assertTrue(new File(tempTemplateDir).isDirectory());
        assertNotNull(execute);
        Mockito.verify(mockLogger).info(Mockito.anyString());


    }


    @Test
    public void test_throw_copy_project_task_exception(@TempDir Path tempDir) throws IOException {

        RunnerLogger mockLogger = Mockito.mock(RunnerLogger.class);
        FileSystem mockFs = Mockito.mock(FileSystem.class);

        String baseDir =
                "src/test/resources-its/com/twlabs/mojos/CookieCutterMojoIT/test_basic_usage_example/";

        CreateTemplateCommandBuilder requestBuilder = new CreateTemplateCommandBuilder();

        CopyProjectTask task = new CopyProjectTask(mockFs);

        String buildDir = baseDir + "target/";

        String tempTemplateDir = tempDir.toFile().getAbsolutePath() + BUILD_TEMPLATE_DIR;

        requestBuilder.withBaseDir(new File(baseDir)).withBuildDir(buildDir)
                .withTemplateDir(tempTemplateDir).withLogger(mockLogger);



        Mockito.doThrow(new IOException()).when(mockFs).copyDirectoryStructure(new File(baseDir),
                new File(tempTemplateDir));

        assertThrows(RuntimeException.class, () -> {
            task.execute(requestBuilder.build());
        });

        Mockito.verify(mockLogger).error(Mockito.anyString());



    }
}
