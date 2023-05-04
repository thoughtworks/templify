package com.twlabs.handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import com.twlabs.exceptions.FileHandlerException;
import com.twlabs.interfaces.FileHandler;
import io.github.yamlpath.YamlPath;

/**
 * YamlHandler
 */
public class YamlHandler implements FileHandler {


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
            throw new FileHandlerException("Problem to read file: "+filePath+"\nor multiples data was found for the query: "+query, e);
        }

        return yamlMap;

    }


    @Override
    public void replace(String filePath, String query, String newValue)
            throws FileHandlerException {
        throw new UnsupportedOperationException("Unimplemented method 'replace'");
    }

    @Override
    public void replace(String filePath, Map<String, String> queryValueMap)
            throws FileHandlerException {
        throw new UnsupportedOperationException("Unimplemented method 'replace'");
    }

}
