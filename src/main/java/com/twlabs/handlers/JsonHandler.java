package com.twlabs.handlers;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.w3c.dom.NodeList;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.internal.filter.ValueNode.JsonNode;
import com.twlabs.HandlerFiles;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

/**
 * JsonHandler
 */
public class JsonHandler implements HandlerFiles {

    public static String readFileAsString(String file) throws Exception {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    @Override
    public NodeList find(String filePath, String query) {

        HashMap<String, String> result = new HashMap<String, String>();

        try {
            Configuration pathConfiguration =
                    Configuration.builder().options(Option.AS_PATH_LIST).build();

            String json = readFileAsString(filePath);

            DocumentContext jsonContext = JsonPath.using(pathConfiguration).parse(json);
            List<String> nodes = jsonContext.read(query);


            DocumentContext parse = JsonPath.parse(json);

            for (String node : nodes) {
                String value = parse.read(node);
                result.put(node, value);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void replace(String filePath, String query, String newValue, String replacedValuesPath) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'replace'");
    }

    @Override
    public void replace(String filePath, Map<String, String> queryValueMap,
            String replacedValuesPath) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'replace'");
    }


}
