package com.twlabs.handlers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import com.github.javafaker.Faker;
import com.twlabs.exceptions.FileHandlerException;



@DisplayNameGeneration(ReplaceUnderscores.class)
public class JavaHandlerTest {

    JavaHandler javaHandler = new JavaHandler();

    String baseDir = "src/test/resources/processador/java";

    Faker faker = new Faker();


    @Test
    public void find_file() throws FileHandlerException {


        String query = "com.myPackage.br";

        Map<String, String> filesMaps = javaHandler.find(baseDir, query);

        assertTrue(filesMaps.containsValue("com/myPackage/br"));

    }



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



    @Test
    public void test_replace_map_directory() throws IOException, FileHandlerException {


        Map<String, String> replaceMap = new HashMap<>();

        String query = "firstTest";// faker.dog().name();
        String name = "cookieCutter1"; // faker.lorem().word();
        replaceMap.put(query, name);

        createFakeProject(baseDir, query);

        String queryTest2 = "secondTest";// faker.color().name();
        String nameCookie2 = "CookieCutter2"; // "faker.lorem().word();
        replaceMap.put(queryTest2, nameCookie2);

        createFakeProject(baseDir, queryTest2);

        javaHandler.replace(baseDir, replaceMap);



        File oldDirectory = new File(baseDir + "/" + query.replace(".", "/"));
        assertFalse(oldDirectory.exists());

        File newDirectory = new File(baseDir + "/" + name.replace(".", "/"));
        assertTrue(newDirectory.exists());

        javaHandler.removePackageDirectory(Paths.get(baseDir), name);

        oldDirectory = new File(baseDir + "/" + queryTest2.replace(".", "/"));
        assertFalse(oldDirectory.exists());

        newDirectory = new File(baseDir + "/" + nameCookie2.replace(".", "/"));
        assertTrue(newDirectory.exists());

        javaHandler.removePackageDirectory(Paths.get(baseDir), nameCookie2);

    }



    private void createFakeProject(String baseDir, String query) throws IOException {

        String packageFolders = query.replace(".", File.separator);


        Path tempDir;
        Path baseDirPath = Paths.get(baseDir);
        if (Files.exists(baseDirPath)) {
            tempDir = Files.createDirectory(Paths.get(baseDir + "/" + packageFolders));

        } else {
            Files.createDirectory(baseDirPath);
            tempDir = Files.createDirectory(Paths.get(baseDir + "/" + packageFolders));
        }



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
