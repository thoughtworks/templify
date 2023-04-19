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
import com.twlabs.HandlerFiles;
import net.minidev.json.JSONArray;

/**
 * JsonHandler
 */
public class JsonHandler implements HandlerFiles {

    public static String readFileAsString(String file) throws Exception {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    @Override
    public NodeList find(String filePath, String jsonp) {

        HashMap<String, String> result = new HashMap<String, String>();

        try {
            Configuration pathConfiguration =
                    Configuration.builder().options(Option.AS_PATH_LIST).build();

            String json = readFileAsString(filePath);

            DocumentContext jsonContext = JsonPath.using(pathConfiguration).parse(json);
            List<String> nodes = jsonContext.read(jsonp);


            DocumentContext parse = JsonPath.parse(json);

            for (String node : nodes) {
                Object value = parse.read(node);

                if (value instanceof String) {
                    result.put(node, value.toString());

                } else if (value instanceof JSONArray) {
                    StringBuffer values = new StringBuffer();
                    JSONArray array = (JSONArray) value;
                    for (int i = 0; i < array.size(); i++) {
                       values.append(array.get(i).toString()); 
                    }

                    result.put(node, value.toString());
                } else if ( value instanceof Map) {
                    Map<String, String> jsonObject = (Map<String, String>) value;
                    // System.out.println(jsonObject);

                } else {
                    throw new UnsupportedOperationException("Unsupported type: " + value.getClass().getName());
                } 
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
