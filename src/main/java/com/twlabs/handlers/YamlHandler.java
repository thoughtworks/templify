package com.twlabs.handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import com.twlabs.exceptions.FileHandlerException;
import com.twlabs.interfaces.FileHandler;
import io.github.yamlpath.YamlPath;

/**
 * YamlHandler is a class that implements the FileHandler interface and extends the FileHandlerOptions class.
 * It provides functionality for handling YAML files.
 * 
 * 
 * Example usage:
 * 
 * YamlHandler yamlHandler = new YamlHandler();
 * 
 * Note: The YamlHandler class requires the SnakeYAML library to be present in the classpath.
 * Note: This class assumes that the yaml|yml files being handled are valid and well-formed.
 * 
 * @see AbstractFileHandler
 * @see FileHandler
 * 
 */
public class YamlHandler extends AbstractFileHandler {


    @Override
    public Map<String, String> find(String filePath, String query) throws FileHandlerException {

        Map<String, String> yamlMap = new HashMap<>();

        try {
            Set<String> yamlSets =
                    YamlPath.from(new FileInputStream(new File(filePath))).read(query);

            for (String yamlSet : yamlSets) {
                yamlMap.put(query, yamlSet);
            }

            if (yamlMap.isEmpty()) {
                throw new FileHandlerException("No data found in file: " + filePath);
            }
        } catch (IOException | IllegalStateException e) {
            throw new FileHandlerException("Problem to read file: " + filePath
                    + "\nor multiples data was found for the query: " + query, e);
        }

        return yamlMap;

    }


    @Override
    public void replace(String filePath, String query, String newValue)
            throws FileHandlerException {

        try {
            String result = YamlPath.from(new FileInputStream(new File(filePath)))
                    .write(query, newValue).dumpAsString();

            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                outputStream.write(result.getBytes());
            }

        } catch (FileNotFoundException e) {
            throw new FileHandlerException("File not found: " + filePath, e);
        } catch (IOException e) {
            throw new FileHandlerException("Problem to read file: " + filePath, e);
        }

    }

    @Override
    public void replace(String filePath, Map<String, String> queryValueMap)
            throws FileHandlerException {

        for (Map.Entry<String, String> entry : queryValueMap.entrySet()) {
            this.replace(filePath, entry.getKey(), entry.getValue());
        }

    }


}
