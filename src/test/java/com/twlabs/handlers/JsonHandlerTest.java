package com.twlabs.handlers;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import com.twlabs.HandlerFiles;

/**
 * JsonHandlerTest
 */
public class JsonHandlerTest {

    HandlerFiles jsonHandler = new JsonHandler();
    final String teste_json = "src/test/resources/processador/json/teste.json";

    @ParameterizedTest
    @CsvSource({"$['name']", "$..['name']", "$['obbies']" , "$['address']"})
    public void test_find(String jsonp) throws Exception {
        this.jsonHandler.find(teste_json, jsonp);
    }

    @Test
    public void test_replace_with_map() throws Exception {

        String filePath = "";
        Map<String, String> queryValueMap = new HashMap<String, String>();
        String replacedValuesPath = "";

        this.jsonHandler.replace(filePath, queryValueMap, replacedValuesPath);
    }


    @Test
    public void test_replace_with_string() throws Exception {

        String filePath = "";
        String queryValueMap = "";
        String replacedValuesPath = "";
        String newValue = "";

        this.jsonHandler.replace(filePath, queryValueMap, newValue, replacedValuesPath);
    }
}
