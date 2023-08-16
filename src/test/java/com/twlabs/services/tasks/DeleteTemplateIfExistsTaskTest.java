
package com.twlabs.services.tasks;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.github.javafaker.Faker;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.twlabs.injetor.ContextDependencyInjection;
import com.twlabs.services.CreateTemplateRequest;
import com.twlabs.services.CreateTemplateRequest.CreateTemplateRequestBuilder;

/**
 * DeleteTemplateIfExistsTaskTest
 */
public class DeleteTemplateIfExistsTaskTest {


    private static final String TEST_PATH = "src/test/resources/tmp";

    private static Faker faker = new Faker();

    LoadConfigurationTask task = new LoadConfigurationTask();
    Injector injector = Guice.createInjector(new ContextDependencyInjection());


    @Test
    public void test_delete_previous_project() {
        injector.injectMembers(task);

        CreateTemplateRequestBuilder requestBuilder = new CreateTemplateRequestBuilder();


        DeleteTemplateIfExistsTask task = new DeleteTemplateIfExistsTask();


        String buildDir = TEST_PATH;



        requestBuilder.withBaseDir(new File(buildDir)).withBuildDir(buildDir)
                .withTemplateDir(buildDir);



        CreateTemplateRequest execute = task.execute(requestBuilder.build());

        assertFalse(new File(TEST_PATH).exists());
        assertNotNull(execute);


    }



    @BeforeAll
    protected static void createTempProject() throws IOException {

        Path tempDir = null;
        Path baseDirPath = Paths.get(TEST_PATH);


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

