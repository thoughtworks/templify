package com.twlabs.handlers;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import com.twlabs.HandlerFiles;

/**
 * JsonHandlerTest
 */
public class JsonHandlerTest {

    HandlerFiles jsonHandler = new JsonHandler();

    @Test
    public void test_find() throws Exception {
        String file = "src/test/resources/processador/json/teste.json";
        String jsonPath = "$['name']";
        this.jsonHandler.find(file, jsonPath);
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
