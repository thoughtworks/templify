package com.thoughtworks.services.tasks;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;
import com.github.javafaker.Faker;
import com.thoughtworks.services.CreateTemplateCommand;
import com.thoughtworks.services.CreateTemplateCommand.CreateTemplateCommandBuilder;
import com.thoughtworks.services.fs.FileSystem;
import com.thoughtworks.services.logger.RunnerLogger;

/**
 * DeleteTemplateIfExistsTaskTest
 */
public class DeleteTemplateIfExistsTaskTest {


    private static Faker faker = new Faker();
    private static final String BUILD_TEMPLATE_DIR = "/template";


    @Test
    public void test_delete_previous_project(@TempDir Path tempDir)
            throws RuntimeException, IOException {

        createTempProject(tempDir);


        RunnerLogger mock = Mockito.mock(RunnerLogger.class);


        DeleteTemplateIfExistsTask task = new DeleteTemplateIfExistsTask();

        CreateTemplateCommandBuilder requestBuilder = new CreateTemplateCommandBuilder();

        String baseDir = tempDir.toFile().getAbsolutePath();
        String tempTemplateDir = tempDir.toFile().getAbsolutePath() + BUILD_TEMPLATE_DIR;

        requestBuilder.withBaseDir(new File(baseDir)).withBuildDir(baseDir + "/target")
                .withTemplateDir(tempTemplateDir).withLogger(mock);

        CreateTemplateCommand execute = task.execute(requestBuilder.build());

        assertFalse(new File(tempTemplateDir).exists());
        assertNotNull(execute);
        Mockito.verify(mock, Mockito.times(2)).info(Mockito.anyString());


    }

    @Test
    public void test_do_nothing_with_no_previous_project(@TempDir Path tempDir) throws IOException {


        RunnerLogger mock = Mockito.mock(RunnerLogger.class);

        DeleteTemplateIfExistsTask task = new DeleteTemplateIfExistsTask();
        CreateTemplateCommandBuilder requestBuilder = new CreateTemplateCommandBuilder();

        String baseDir = tempDir.toFile().getAbsolutePath();
        String tempTemplateDir = tempDir.toFile().getAbsolutePath() + "/template";

        requestBuilder.withBaseDir(new File(baseDir)).withBuildDir(baseDir + "/target")
                .withTemplateDir(tempTemplateDir).withLogger(mock);

        CreateTemplateCommand execute = task.execute(requestBuilder.build());

        assertFalse(new File(tempTemplateDir).exists());
        assertNotNull(execute);
        Mockito.verify(mock, Mockito.times(1)).info(Mockito.anyString());


    }



    @Test
    public void test_throw_remove_directory_exceptions(@TempDir Path tempDir) throws IOException {
        // Assigning
        RunnerLogger mockLogger = Mockito.mock(RunnerLogger.class);

        FileSystem mockFs = Mockito.mock(FileSystem.class);


        DeleteTemplateIfExistsTask task = new DeleteTemplateIfExistsTask(mockFs);
        CreateTemplateCommandBuilder requestBuilder = new CreateTemplateCommandBuilder();

        String baseDir = tempDir.toFile().getAbsolutePath();
        String tempTemplateDir = tempDir.toFile().getAbsolutePath() + "/template";


        requestBuilder.withBaseDir(new File(baseDir)).withBuildDir(baseDir + "/target")
                .withTemplateDir(tempTemplateDir).withLogger(mockLogger);


        // Act
        Mockito.doThrow(new IOException()).when(mockFs).fileExists(Mockito.anyString());
        assertThrows(RuntimeException.class, () -> {
            task.execute(requestBuilder.build());

        });

        // Assert
        Mockito.verify(mockLogger).error(Mockito.anyString());


    }



    protected void createTempProject(Path tempDir) throws RuntimeException, IOException {

        Path baseDirPath = Paths.get(tempDir.toFile().getAbsolutePath() + BUILD_TEMPLATE_DIR);

        if (!Files.exists(baseDirPath)) {
            tempDir = Files.createDirectory(baseDirPath);
        }

        for (int i = 0; i < 3; i++) {

            String tempFileName = faker.color().name();
            Path tempFile = Files.createTempFile(tempDir, tempFileName, ".java");

            Files.write(tempFile,
                    ("packag fake.package.project;\npublic class " + tempFileName + "{ }")
                            .getBytes());


            String newFakeFolder = faker.dog().name();

            Path newTempDir = Files.createTempDirectory(tempDir, newFakeFolder);

            for (int j = 0; j < 2; j++) {
                String newTempFileName = faker.cat().name();
                Path newTempFile = Files.createTempFile(newTempDir, newTempFileName, ".java");
                Files.write(newTempFile, ("package fake.package.project." + newFakeFolder
                        + ";\npublic class " + newTempFileName + "{ }").getBytes());
            }


        }


    }
}
