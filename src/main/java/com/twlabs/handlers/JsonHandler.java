package com.twlabs.handlers;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.PathNotFoundException;
import com.jayway.jsonpath.internal.JsonFormatter;
import com.twlabs.HandlerFiles;
import com.twlabs.HandlerFilesException;

/**
 * JsonHandler ->
 */
public class JsonHandler implements HandlerFiles {

    protected static String readFileAsString(String file) throws Exception {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    @Override
    public Map<String, String> find(String filePath, String jsonp) throws HandlerFilesException {

        HashMap<String, String> result;

        try {
            Configuration pathConfiguration =
                    Configuration.builder().options(Option.AS_PATH_LIST).build();

            String json = readFileAsString(filePath);

            DocumentContext jsonContext = JsonPath.using(pathConfiguration).parse(json);
            List<String> nodes = jsonContext.read(jsonp);
            DocumentContext parse = JsonPath.parse(json);

            result = new HashMap<String, String>();

            for (String node : nodes) {
                Object value = parse.read(node);

                if (!(value instanceof String))
                    throw new UnsupportedOperationException(
                            "Unsupported type: " + value.getClass().getName());

                result.put(node, value.toString());
            }

        } catch (UnsupportedOperationException e) {
            throw e;
        } catch (PathNotFoundException notFound) {
            throw new HandlerFilesException(jsonp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public void replace(String file, String jsonp, String newValue, String replacedValuesPath) {
        try {
            String json = readFileAsString(file);

            DocumentContext jsonContext = JsonPath.parse(json);

            jsonContext.set(jsonp, newValue);

            String str = JsonFormatter.prettyPrint(jsonContext.jsonString());

            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] strToBytes = str.getBytes();
            outputStream.write(strToBytes);

            outputStream.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void replace(String filePath, Map<String, String> queryValueMap,
            String replacedValuesPath) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'replace'");
    }


}
