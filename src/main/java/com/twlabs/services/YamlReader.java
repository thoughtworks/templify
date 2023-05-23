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
    public PluginConfig read(String configFilePath) {
        Yaml yaml = new Yaml();
        try {
            InputStream inputStream = Files.newInputStream(Paths.get(configFilePath));

            return yaml.loadAs(inputStream, PluginConfig.class);
        } catch (IOException e) {
            throw new RuntimeException(
                    "It's not possible to read the config file: " + configFilePath+" \nmake sure the config file exists", e);
        }
    }
}
