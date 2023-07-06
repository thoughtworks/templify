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
import com.twlabs.interfaces.FileHandler;
import com.twlabs.exceptions.FileHandlerException;
import com.twlabs.model.settings.Placeholder;

/**
 * JsonHandler ->
 */
public class PlainTextHandler implements FileHandler {

    protected static String readFileAsString(String file) throws Exception {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    @Override
    public Map<String, String> find(String filePath, String jsonp) throws FileHandlerException {

        return null;
    }

    @Override
    public void replace(String file, String key, String newValue) {
        try {
            String text = readFileAsString(file);
            String updatedContent = text.replaceAll(key, newValue);


            try (FileOutputStream outputStream = new FileOutputStream(file)) {

                byte[] strToBytes = updatedContent.getBytes();

                outputStream.write(strToBytes);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void replace(String filePath, Map<String, String> queryValueMap) {

        for (Map.Entry<String, String> entry : queryValueMap.entrySet()) {
            this.replace(filePath, entry.getKey(), entry.getValue());
        }
    }

}
