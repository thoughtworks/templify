package com.twlabs.handlers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import com.github.javafaker.Faker;
import com.twlabs.exceptions.FileHandlerException;
import com.twlabs.interfaces.FileHandler;

@DisplayNameGeneration(ReplaceUnderscores.class)
public class JavaHandlerTest {

    JavaHandler javaHandler = new JavaHandler();

    String baseDir = "src/test/resources/processador/java";

    Faker faker = new Faker();

    @Test
    public void test_change_directory() throws FileHandlerException, IOException {

        String query = faker.dog().name();
        String name = faker.lorem().word();

        createFakeProject(baseDir, query);

        javaHandler.replace(baseDir, query, name);

        File oldDirectory = new File(baseDir + "/" + query.replace(".", "/"));
        assertFalse(oldDirectory.exists());

        File newDirectory = new File(baseDir + "/" + name.replace(".", "/"));
        assertTrue(newDirectory.exists());


        javaHandler.removePackageDirectory(Paths.get(baseDir), name);

        // assertTrue(false);
    }

    @Test
    public void test_remove_directory() throws FileHandlerException, IOException {


        String query = faker.color().name();

        createFakeProject(baseDir, query);

        javaHandler.removePackageDirectory(Paths.get(baseDir), query);
        File removedDirectory = new File(baseDir + "/" + query.replace(".", "/"));

        assertFalse(removedDirectory.exists());
    }



    private void createFakeProject(String baseDir, String query) throws IOException {

        String packageFolders = query.replace(".", File.separator);



        Path tempDir = Files.createDirectory(Paths.get(baseDir + "/" + packageFolders));


        for (int i = 0; i < 3; i++) {
            String tempFileName = faker.color().name();
            Path tempFile = Files.createTempFile(tempDir, tempFileName, ".java");

            Files.write(tempFile,
                    ("package " + query + ";\npublic class " + tempFileName + "{ }").getBytes());


            String newFakeFolder = faker.dog().name();

            Path newTempDir = Files.createTempDirectory(tempDir, newFakeFolder);
            for (int j = 0; j < 2; j++) {
                String newTempFileName = faker.cat().name();
                Path newTempFile = Files.createTempFile(newTempDir, newTempFileName, ".java");
                Files.write(newTempFile, ("package " + query + "." + newFakeFolder
                        + ";\npublic class " + newTempFileName + "{ }").getBytes());
            }


        }


    }


}
