package com.thoughtworks.kinds.handlers.yamlhandler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import com.github.javafaker.Faker;
import com.thoughtworks.kinds.api.FileHandler;
import com.thoughtworks.kinds.api.FileHandlerException;

@DisplayNameGeneration(ReplaceUnderscores.class)
public class YamlFileHandlerTest {

    FileHandler yamlHandler = new YamlFileHandler();


    Faker faker = new Faker();

    final String test_yaml = "src/test/resources/processador/yaml/test.yml";

    @ParameterizedTest
    @CsvSource({"metadata.name, example", "kind, Deployment",
            "placeholders[0].query, /project/groupId",
            "placeholders[0].name, templify.param.groupId",
            "placeholders[2].query, /project/dependencies/dependency/scope[text()='test']",
            "placeholders[2].name, templify.replace.map.scopes"})
    public void test_find_simple_names_must_return_values(String query, String expected)
            throws FileHandlerException {
        assertThat(this.yamlHandler.find(test_yaml, query).get(query)).isNotNull().isNotEmpty()
                .isEqualTo(expected);
    }



    @ParameterizedTest
    @CsvSource({"src/test/resources/processador/yaml/empty.yml"})
    public void test_file_is_empty(String filePath) throws FileHandlerException {
        assertThrows(FileHandlerException.class, () -> this.yamlHandler.find(filePath, "name"));
    }



    @ParameterizedTest
    @CsvSource({"metadata.notFound", "NotFound"})
    public void test_find_not_found(String query) throws FileHandlerException {
        assertThrows(FileHandlerException.class, () -> this.yamlHandler.find(test_yaml, query));
    }



    @ParameterizedTest
    @CsvSource({"placeholders.query"})
    public void test_find_same_path_different_values(String query) throws FileHandlerException {
        assertThrows(FileHandlerException.class, () -> this.yamlHandler.find(test_yaml, query));
    }


    @ParameterizedTest
    @CsvSource({"metadata.name", "kind", "placeholders[0].query", "placeholders[0].name",
            "placeholders[2].query", "placeholders[2].name"})
    public void test_replace(String query) throws FileHandlerException, IOException {

        String newName = faker.name().fullName().concat("---");
        String filename = faker.lorem().word().toLowerCase();

        final Path fileForTest = Files.createTempFile(filename, ".yml");
        FileUtils.copyFile(Paths.get(test_yaml).toFile(), fileForTest.toFile());

        this.yamlHandler.replace(fileForTest.toAbsolutePath().toString(), query, newName);

        Map<String, String> results =
                this.yamlHandler.find(fileForTest.toAbsolutePath().toString(), query);

        assertThat(results).isNotNull().isNotEmpty().containsValue(newName);
    }


    @Test
    public void test_replace_map() throws IOException, FileHandlerException {
        String kindQuery = "kind";
        String newKind = faker.name().fullName();

        String placeholder0Query = "placeholders[0].query";
        String newPlaceholder0Query = faker.name().fullName();

        String placeholder2Query = "placeholders[2].query";
        String newPlaceholder2Query = faker.name().fullName();

        String placeholder0Name = "placeholders[0].name";
        String newPlaceholder0Name = faker.name().fullName();

        String placeholder1Name = "placeholders[1].name";
        String newPlaceholder1Name = faker.name().fullName();

        Map<String, String> replaces = new HashMap<>();
        replaces.put(kindQuery, newKind);
        replaces.put(placeholder0Query, newPlaceholder0Query);
        replaces.put(placeholder2Query, newPlaceholder2Query);
        replaces.put(placeholder0Name, newPlaceholder0Name);
        replaces.put(placeholder1Name, newPlaceholder1Name);

        String filename = faker.lorem().word().toLowerCase();
        final Path fileForTest = Files.createTempFile(filename, ".yml");
        FileUtils.copyFile(Paths.get(test_yaml).toFile(), fileForTest.toFile());

        this.yamlHandler.replace(fileForTest.toAbsolutePath().toString(), replaces);

        Map<String, String> results =
                this.yamlHandler.find(fileForTest.toAbsolutePath().toString(), kindQuery);
        assertThat(results).isNotNull().isNotEmpty().containsValue(newKind);

        results = this.yamlHandler.find(fileForTest.toAbsolutePath().toString(), placeholder0Query);
        assertThat(results).isNotNull().isNotEmpty().containsValue(newPlaceholder0Query);

        results = this.yamlHandler.find(fileForTest.toAbsolutePath().toString(), placeholder2Query);
        assertThat(results).isNotNull().isNotEmpty().containsValue(newPlaceholder2Query);

        results = this.yamlHandler.find(fileForTest.toAbsolutePath().toString(), placeholder0Name);
        assertThat(results).isNotNull().isNotEmpty().containsValue(newPlaceholder0Name);

        results = this.yamlHandler.find(fileForTest.toAbsolutePath().toString(), placeholder1Name);
        assertThat(results).isNotNull().isNotEmpty().containsValue(newPlaceholder1Name);

    }
}
