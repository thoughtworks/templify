package com.twlabs.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.yaml.snakeyaml.Yaml;
import com.twlabs.interfaces.ConfigReader;
import com.twlabs.model.PluginConfig;

/**
 * YamlReader
 */
public class YamlReader implements ConfigReader {

    @Override
    public PluginConfig read(String configFilePath) throws IOException { //caminho absoluto
        Yaml yaml = new Yaml();
        
        InputStream inputStream = Files.newInputStream(Paths.get(configFilePath));

        return yaml.loadAs(inputStream, PluginConfig.class);
    }
}
