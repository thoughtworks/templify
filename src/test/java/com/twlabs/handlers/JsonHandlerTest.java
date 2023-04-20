package com.twlabs.handlers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import com.github.javafaker.Faker;
import com.twlabs.HandlerFiles;
import com.twlabs.HandlerFilesException;

/**
 * JsonHandlerTest
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class JsonHandlerTest {

    HandlerFiles jsonHandler = new JsonHandler();
    Faker faker = new Faker();
    final String teste_json = "src/test/resources/processador/json/teste.json";
    final String teste_empty_json = "src/test/resources/processador/json/empty.json";

    @ParameterizedTest
    @CsvSource({"$['name']", "$..['name']"})
    public void test_find_simple_types_must_return_values(String jsonp)
            throws HandlerFilesException {
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
        assertThrows(HandlerFilesException.class, () -> this.jsonHandler.find(teste_json, jsonp));
    }

    @Test
    public void test_replace_with_map() throws Exception {

        String query = "$['name']";
        String newValue = faker.name().fullName();

        String filename = faker.lorem().word().toLowerCase();
        final Path fileForTest = Files.createTempFile(filename, ".json");
        FileUtils.copyFile(Paths.get(teste_json).toFile(), fileForTest.toFile());

        this.jsonHandler.replace(fileForTest.toAbsolutePath().toString(), query, newValue, null);

        Map<String, String> results =
                this.jsonHandler.find(fileForTest.toAbsolutePath().toString(), query);

        assertThat(results).isNotNull().isNotEmpty().containsValue(newValue);
    }


    @Test
    @Disabled("Not implemented yet")
    public void test_replace_with_string() throws Exception {

        String filePath = "";
        String queryValueMap = "";
        String replacedValuesPath = "";
        String newValue = "";

        this.jsonHandler.replace(filePath, queryValueMap, newValue, replacedValuesPath);
    }
}
