package com.twlabs.kinds.handlers.jsonhandler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import com.github.javafaker.Faker;
import com.twlabs.kinds.api.FileHandler;
import com.twlabs.kinds.api.FileHandlerException;

/**
 * JsonHandlerTest
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class JsonHandlerTest {

    FileHandler jsonHandler = new JsonHandler();
    Faker faker = new Faker();
    final String teste_json = "src/test/resources/processador/json/teste.json";
    final String teste_empty_json = "src/test/resources/processador/json/empty.json";

    @ParameterizedTest
    @CsvSource({"$['name']", "$..['name']"})
    public void test_find_simple_types_must_return_values(String jsonp)
            throws FileHandlerException {
        assertThat(this.jsonHandler.find(teste_json, jsonp)).isNotNull().isNotEmpty();
    }

    @ParameterizedTest
    @CsvSource({"$['hobbies']"})
    public void test_find_lists_must_throw_unsupported_operation_excep(String jsonp) {
        assertThrows(UnsupportedOperationException.class,
                () -> this.jsonHandler.find(teste_json, jsonp));
    }

    @ParameterizedTest
    @CsvSource({"$['address']"})
    public void test_find_objects_must_throw_unsupported_operation_excep(String jsonp) {
        assertThrows(UnsupportedOperationException.class,
                () -> this.jsonHandler.find(teste_json, jsonp));

    }

    @Test
    public void test_find_with_empty_file_must_throw_unsupported_op() {
        assertThrows(UnsupportedOperationException.class,
                () -> this.jsonHandler.find(teste_empty_json, "$['name']"));
    }

    @ParameterizedTest
    @CsvSource({"$['xyzpto']"})
    public void test_find_not_found_must_throw_not_found_excep(String jsonp) {
        assertThrows(FileHandlerException.class, () -> this.jsonHandler.find(teste_json, jsonp));
    }

    @Test
    public void test_replace_with_map() throws Exception {

        String nameQuery = "$['name']";
        String newName = faker.name().fullName();

        String filename = faker.lorem().word().toLowerCase();

        final Path fileForTest = Files.createTempFile(filename, ".json");
        FileUtils.copyFile(Paths.get(teste_json).toFile(), fileForTest.toFile());

        this.jsonHandler.replace(fileForTest.toAbsolutePath().toString(), nameQuery, newName);

        Map<String, String> results =
                this.jsonHandler.find(fileForTest.toAbsolutePath().toString(), nameQuery);

        assertThat(results).isNotNull().isNotEmpty().containsValue(newName);
    }


    @Test
    public void test_replace_with_maps() throws Exception {

        String nameQuery = "$['name']";
        String newName = faker.name().fullName();

        String ageQuery = "$['age']";
        String newAge = faker.numerify("##");

        Map<String, String> replaces = new HashMap<>();
        replaces.put(nameQuery, newName);
        replaces.put(ageQuery, newAge);

        String filename = faker.lorem().word().toLowerCase();
        final Path fileForTest = Files.createTempFile(filename, ".json");
        FileUtils.copyFile(Paths.get(teste_json).toFile(), fileForTest.toFile());

        this.jsonHandler.replace(fileForTest.toAbsolutePath().toString(), replaces);

        Map<String, String> results =
                this.jsonHandler.find(fileForTest.toAbsolutePath().toString(), nameQuery);

        assertThat(results).isNotNull().isNotEmpty().containsValue(newName);

    }
}
