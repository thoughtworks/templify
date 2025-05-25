package com.thoughtworks.kinds.handlers.directoryhandler;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.thoughtworks.kinds.api.FileHandlerException;

public class DirectoryHandlerTest {

    private DirectoryHandler directoryHandler = new DirectoryHandler();

    @ParameterizedTest
    @Disabled
    @CsvSource({ "'src/test/resources/processador/directory', ''",
            "src/test/resources/processador/directory, 'static'", })
    public void test_find_simple_directory(String dirtectyPath, String queryToFind) throws FileHandlerException {

        Map<String, String> result = this.directoryHandler.find(dirtectyPath, queryToFind);

        assertThat(result).isNotNull().isNotEmpty();

    }

    @ParameterizedTest
    @Disabled
    @CsvSource({ "'src/test/resources/processador/directory/static/folder/empty.txt'", })
    public void test_find_directory_as_file(String file) throws FileHandlerException {
        assertThrows(FileHandlerException.class, () -> this.directoryHandler.find(file, ""));

    }

    @ParameterizedTest
    @Disabled
    @CsvSource({
            "'src/test/resources/processador/directory/static/folder/br/com/projeto', 'src/test/resources/processador/directory/static/{{cookiecuter.projeto}}'" })
    public void test_copy_directory(String sourceDirectory, String targetDirectory) throws FileHandlerException {
        boolean result = this.directoryHandler.copyDirectory(sourceDirectory, targetDirectory);

        assertThat(result).isTrue();

        assertThat(Files.exists(Paths.get(targetDirectory))).isTrue();
        boolean deleteResult = this.directoryHandler.deleteDirectory(targetDirectory);
        assertThat(deleteResult).isTrue();
        assertThat(Files.exists(Paths.get(targetDirectory))).isFalse();
    }

    @ParameterizedTest
    @Disabled
    @CsvSource({
            "'src/test/resources/processador/directory/static/folder'", })
    public void test_directory_exists(String sourcePath) {
        boolean result = this.directoryHandler.isDirectoryExists(sourcePath);

        assertThat(result).isTrue();

    }

    @ParameterizedTest
    @Disabled
    @CsvSource({
            "'src/test/resources/processador/directory/static/non_exist'" })
    public void test_directory_not_exists(String sourcePath) {
        boolean result = this.directoryHandler.isDirectoryExists(sourcePath);

        assertThat(result).isFalse();

    }

    @ParameterizedTest
    @Disabled
    @CsvSource({
            "'src/test/resources/processador/directory/static/folder'",
    })
    public void test_delete_directory(String sourcePath) throws FileHandlerException {

        String targetDirectory = sourcePath + "_copy";

        this.directoryHandler.copyDirectory(sourcePath, targetDirectory);

        assertThat(this.directoryHandler.isDirectoryExists(targetDirectory)).isTrue();

        boolean result = this.directoryHandler.deleteDirectory(targetDirectory);

        assertThat(result).isTrue();
        assertThat(this.directoryHandler.isDirectoryExists(targetDirectory)).isFalse();
    }

    @ParameterizedTest
    @Disabled
    @CsvSource({ "/no_existo" })
    public void test_delete_directory_not_exists(String sourcePath) throws FileHandlerException {
        boolean result = this.directoryHandler.deleteDirectory(sourcePath);

        assertThat(result).isFalse();
    }

    @ParameterizedTest
    @Disabled
    @CsvSource({ "'src/test/resources/processador/directory/static/folder', 'br/com/projeto'",
            "src/test/resources/processador/directory/static/folder, ''", })
    public void test_get_right_path_to_delete(String sourcePath, String query) throws FileHandlerException {
        String pathResult = this.directoryHandler.getDirectoryToBeDeleted(sourcePath, query);

        String firstFolder = query.split(Pattern.quote(File.separator))[0];
        String expected = sourcePath + File.separator + firstFolder;

        assertThat(Files.exists(Paths.get(pathResult))).isTrue();
        assertThat(pathResult).isEqualTo(expected);

    }

    @ParameterizedTest
    @Disabled
    @CsvSource({
            "'src/test/resources/processador/directory/static/folder', 'br/com/projeto', '{{placeholder}}' ",
    })
    public void test_replace(String filePath, String query, String newValue) throws FileHandlerException {

        String testReplaceDirectory = filePath + "_replace";
        this.directoryHandler.copyDirectory(filePath, testReplaceDirectory);

        this.directoryHandler.replace(testReplaceDirectory, query, newValue);

        String result = testReplaceDirectory + File.separator + newValue;
        assertThat(this.directoryHandler.isDirectoryExists(result)).isTrue();

        this.directoryHandler.deleteDirectory(testReplaceDirectory);

    }

    @Test
    public void test_replace_with_empty_string() {
        String filePath = "";
        String query = "";
        String newValue = "";
        assertThrows(FileHandlerException.class, () -> this.directoryHandler.replace(filePath, query, newValue));
    }

    @Test
    public void test_replace_with_map() {
        Map<String, String> queryValueMap = new HashMap<String, String>();
        queryValueMap.put("br/com/projeto", "{{placeholder}}");
        assertThrows(UnsupportedOperationException.class, () -> this.directoryHandler.replace("", queryValueMap));
    }
}
