package com.twlabs;

import java.io.InputStream;
import org.yaml.snakeyaml.Yaml;

/**
 * YamlReader
 */
public class YamlReader implements ConfigReader {

    @Override
    public ConfigMappings read(String configFilePath) {
        // TODO Auto-generated method stub
        //
        Yaml yaml = new Yaml();

        InputStream inputStream =
                this.getClass().getClassLoader().getResourceAsStream(configFilePath);
        return yaml.loadAs(inputStream, YamlMappings.class);
    }
}
