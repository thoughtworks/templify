
package com.twlabs.services.tasks;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import com.github.javafaker.Faker;
import com.twlabs.services.CreateTemplateRequest;
import com.twlabs.services.CreateTemplateRequest.CreateTemplateRequestBuilder;

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

        DeleteTemplateIfExistsTask task = new DeleteTemplateIfExistsTask();

        CreateTemplateRequestBuilder requestBuilder = new CreateTemplateRequestBuilder();

        String baseDir = tempDir.toFile().getAbsolutePath();
        String tempTemplateDir = tempDir.toFile().getAbsolutePath() + BUILD_TEMPLATE_DIR;

        requestBuilder.withBaseDir(new File(baseDir)).withBuildDir(baseDir + "/target")
                .withTemplateDir(tempTemplateDir);

        CreateTemplateRequest execute = task.execute(requestBuilder.build());

        assertFalse(new File(tempTemplateDir).exists());
        assertNotNull(execute);


    }



    protected void createTempProject(Path tempDir) throws RuntimeException, IOException {

        Path baseDirPath = tempDir;

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

