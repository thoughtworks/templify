package com.twlabs.handlers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import com.twlabs.HandlerFiles;

/**
 * JsonHandlerTest
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class JsonHandlerTest {

    HandlerFiles jsonHandler = new JsonHandler();
    final String teste_json = "src/test/resources/processador/json/teste.json";

    @ParameterizedTest
    @CsvSource({"$['name']", "$..['name']"})
    public void test_find_simple_types_must_return_values(String jsonp) {
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

    @ParameterizedTest
    @CsvSource({"$['xyzpto']"})
    public void test_find_not_found_must_throw_not_found_excep(String jsonp) {
        assertThrows(UnsupportedOperationException.class,
                () -> this.jsonHandler.find(teste_json, jsonp));
    }

    @Test
    @Disabled("Not implemented yet")
    public void test_replace_with_map() throws Exception {

        String filePath = "";
        Map<String, String> queryValueMap = new HashMap<String, String>();
        String replacedValuesPath = "";

        this.jsonHandler.replace(filePath, queryValueMap, replacedValuesPath);
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
