package com.twlabs.handlers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;
import com.twlabs.exceptions.FileHandlerException;
import com.twlabs.interfaces.FileHandler;
import io.github.yamlpath.YamlPath;
import io.github.yamlpath.utils.StringUtils;

/**
 * YamlHandler
 */
public class YamlHandler implements FileHandler {


    private InputStream stringToInputStream(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.newInputStream(path);
    }

    @Override
    public Map<String, String> find(String filePath, String query) throws FileHandlerException {

        InputStream inputStream;
        try {
            inputStream = stringToInputStream(filePath);
        
            System.out.println("---"); 

            String yamlSets = YamlPath.from(inputStream).readSingle(query);

            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;

    }


    @Override
    public void replace(String filePath, String query, String newValue)
            throws FileHandlerException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'replace'");
    }

    @Override
    public void replace(String filePath, Map<String, String> queryValueMap)
            throws FileHandlerException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'replace'");
    }

}
