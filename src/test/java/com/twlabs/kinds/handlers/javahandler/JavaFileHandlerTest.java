package com.twlabs.kinds.handlers.javahandler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.Test;
import com.github.javafaker.Faker;
import com.twlabs.kinds.api.FileHandlerException;



@DisplayNameGeneration(ReplaceUnderscores.class)
// JavaHandlerTest#test_change_java_package
public class JavaFileHandlerTest {

    JavaFileHandler javaHandler = new JavaFileHandler();

    static String query = "";
    static String baseDir = "src/test/resources/processador/java/dynamic";
    static String staticBaseDir = "src/test/resources/processador/java/static";
    static String java_test = staticBaseDir + "/com/otherPackage/br/OtherClass.java";

    Faker faker = new Faker();

    @AfterAll
    public static void cleanFolders() throws IOException {
        FileUtils.deleteDirectory(new File(baseDir));
    }

    @Test
    public void find_file() throws FileHandlerException {

        String query = "com.myPackage.br";

        String file = javaHandler.findDir(staticBaseDir + "", query);

        assertTrue(file.contains("com/myPackage/br"));

    }


    @Test
    public void find_file_not_found() throws FileHandlerException {
        String query = "com.Not.Found";

        assertThrows(FileHandlerException.class, () -> javaHandler.find(baseDir, query))
                .getMessage().contains("Path not found");
    }


    @Test
    public void test_change_java_package() throws IOException, FileHandlerException {

        String packageFolder = "myTestPackage";
        String file = "MyClass";

        Path tempDir = Files.createDirectory(
                Paths.get(baseDir + "/" + packageFolder.replace(".", File.separator)));

        Path newTempFile = Files.createFile(Paths.get(tempDir.toString() + "/" + file + ".java"));

        Files.write(newTempFile,
                ("package " + packageFolder + ";\npublic class " + file + "{ }").getBytes());

        assertTrue(Files.exists(newTempFile));

        String replace = "CookieCutter.newPackage";

        System.out.println("baseDir: " + baseDir + " packageFolder: " + packageFolder + " replace: "
                + replace);
        javaHandler.replace(baseDir, packageFolder, replace);

        assertFalse(Files.exists(newTempFile));


        String newContent =
                Files.readString(Paths.get(baseDir + "/" + replace + "/" + file + ".java"));

        assertTrue(newContent.contains("package " + replace));


        Path dirToDelete = Paths.get(baseDir + "/" + replace);

        if (Files.isDirectory(dirToDelete)) {
            try {
                Files.walk(dirToDelete).sorted(Comparator.reverseOrder()).map(Path::toFile)
                        .forEach(File::delete);
            } catch (IOException e) {
                throw new FileHandlerException(
                        "It was not possible to remove the directory: " + dirToDelete.toString(),
                        e);
            }
        }

        assertFalse(Files.exists(dirToDelete));

    }


    @Test
    public void test_change_directory() throws FileHandlerException, IOException {

        query = faker.dog().name();
        String name = faker.lorem().word();

        createFakeProject(baseDir, query);

        javaHandler.replace(baseDir, query, name);

        File oldDirectory = new File(baseDir + "/" + query.replace(".", "/"));
        assertFalse(oldDirectory.exists());

        File newDirectory = new File(baseDir + "/" + name.replace(".", "/"));
        assertTrue(newDirectory.exists());


        javaHandler.removePackageDirectory(baseDir, name);

        // assertTrue(false);

    }

    @Test
    public void test_remove_directory() throws FileHandlerException, IOException {


        String query = faker.color().name();

        createFakeProject(baseDir, query);

        javaHandler.removePackageDirectory(baseDir, query);
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


        for (Map.Entry<String, String> entry : replaceMap.entrySet()) {
            String currentQuery = entry.getKey();
            String currentName = entry.getValue();

            File oldDirectory = new File(baseDir + "/" + currentQuery.replace(".", "/"));
            assertFalse(oldDirectory.exists());

            File newDirectory = new File(baseDir + "/" + currentName.replace(".", "/"));
            assertTrue(newDirectory.exists());

            javaHandler.removePackageDirectory(baseDir, currentName);
            assertFalse(newDirectory.exists());
        }
    }


    @Test
    public void test_find_java_files() throws FileHandlerException {

        Map<String, String> javaFiles = javaHandler.findJavaFiles(staticBaseDir);

        assertTrue(javaFiles.size() > 0);
        assertTrue(javaFiles.containsKey("com/myPackage/br/MyClass.java"));
        assertTrue(javaFiles.containsKey("com/otherPackage/br/OtherClass.java"));
        assertTrue(javaFiles.containsKey("com/myPackage/br/MySecondClass.java"));

    }


    @ParameterizedTest
    @CsvSource({
            "'/Main.java', 'com.myPackage.br', '1'",
            "'/Main.java','com.otherPackage.br', '1' ",
            "'/com/otherPackage/br/OtherClass.java', 'org.junit.jupiter.api', '4' ",
            "'/com/myPackage/br/MyClass.java','com.myPackage.br' , '2' ",
            "'/com/myPackage/br/MySecondClass.java', 'java.util', '3' ",
    })
    public void test_replace_content_java_file(String filePath, String query, String qtd)
            throws FileHandlerException, IOException {
        final Path javaFile = fileForTest(staticBaseDir + filePath);
        String replaceFor = "CookieCutter";

        Map<String, String> beforeChange = javaHandler.findJavaFileContent(javaFile, query);

        javaHandler.replaceJavaFileContent(javaFile, query, replaceFor);

        Map<String, String> afterChange = javaHandler.findJavaFileContent(javaFile, replaceFor);

        assertThat(beforeChange).isNotNull().isNotEmpty().containsKey(query).containsValue(qtd);
        assertThat(afterChange).isNotNull().isNotEmpty().containsKey(replaceFor).containsValue(qtd);


        afterChange = javaHandler.findJavaFileContent(javaFile, query);
        assertThat(afterChange).isNotNull().isEmpty();

    }


    @ParameterizedTest
    @CsvSource({
            "'com.myPackage.br', 'Main.java,com/myPackage/br/MyClass.java,com/myPackage/br/MySecondClass.java,com/otherPackage/br/OtherClass.java', '4'",
            "'com.otherPackage.br', 'Main.java,com/otherPackage/br/OtherClass.java' , '2'"})
    public void test_find_java_files_with_match_content(String match, String filesList,
            String count)
            throws FileHandlerException, IOException {
        List<String> expectedFiles = Arrays.asList(filesList.trim().split(","));
        Path baseDir = Paths.get(staticBaseDir);


        Map<String, String> staticFiles = javaHandler.findJavaFiles(staticBaseDir);


        List<Path> result =
                javaHandler.findJavaFilesWithMatchContent(staticFiles, match);

        int aux = 0;
        for (Path path : result) {
            Path relativePath = baseDir.toAbsolutePath().relativize(path);

            assertTrue(expectedFiles.contains(relativePath.toString()),
                    "Should be " + expectedFiles + " but was " + relativePath.toString());
            aux++;
        }
        assertTrue(aux == Integer.parseInt(count),
                "Should be " + count + " files found but was " + aux);

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

    private Path fileForTest(String path) throws IOException {
        String fileName = faker.lorem().word().toLowerCase();
        final Path fileForTest = Files.createTempFile(fileName, ".java");
        FileUtils.copyFile(Paths.get(path).toFile(), fileForTest.toFile());
        return fileForTest;

    }

}
