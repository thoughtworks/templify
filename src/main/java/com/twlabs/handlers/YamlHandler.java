package com.twlabs.handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import com.twlabs.exceptions.FileHandlerException;
import com.twlabs.interfaces.FileHandler;
import io.github.yamlpath.YamlPath;

/**
 * YamlHandler
 */
public class YamlHandler implements FileHandler {


    @Override
    public Map<String, String> find(String filePath, String query) throws FileHandlerException {

        try {
            System.out.println("---");

            String yamlSets =
                    YamlPath.from(new FileInputStream(new File(filePath))).readSingle(query);

            System.out.println(yamlSets);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

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
