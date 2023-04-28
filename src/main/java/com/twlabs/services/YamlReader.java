package com.twlabs.services;

import java.io.InputStream;
import org.yaml.snakeyaml.Yaml;
import com.twlabs.interfaces.ConfigReader;
import com.twlabs.model.YamlMappings;

/**
 * YamlReader
 */
public class YamlReader implements ConfigReader {

    @Override
    public YamlMappings read(String configFilePath) {
        Yaml yaml = new Yaml();

        InputStream inputStream =
                this.getClass().getClassLoader().getResourceAsStream(configFilePath);
        return yaml.loadAs(inputStream, YamlMappings.class);
    }
}
