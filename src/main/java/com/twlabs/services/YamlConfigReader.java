package com.twlabs.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.yaml.snakeyaml.Yaml;
import com.twlabs.interfaces.ConfigReader;
import com.twlabs.model.settings.PluginConfig;

public class YamlConfigReader implements ConfigReader {

    /**
     * Maps a YAML file to a specified type.
     *
     * This method reads a YAML file from the specified file path and maps it to the specified type
     * using the Jackson library.
     *
     * @param yamlFilePath the path to the YAML file to be mapped
     * @param type the class representing the type to which the YAML file should be mapped
     * @return an instance of the specified type with the data from the YAML file
     * @throws IOException if an I/O error occurs while reading the YAML file
     * @throws IllegalArgumentException if the specified file path is null or empty
     * @throws IllegalArgumentException if the specified type is null
     * @throws IllegalArgumentException if the specified type is not a valid class
     * @throws MappingException if the YAML file cannot be mapped to the specified type
     *
     *         Usage example:
     *
     *         <pre>
     *
     *         <PersonDTO>mapYamlToType(personYamlPath, PersonDTO.class);
     *
     *         </pre>
     *
     * @see Yaml
     * @see Yaml#loadAs
     */
    public <T> T mapYamlToType(String yamlFilePath, Class<T> type) throws IOException {

        T result = null;

        try {
            Yaml yaml = new Yaml();

            InputStream inputStream = Files.newInputStream(Paths.get(yamlFilePath));

            result = yaml.loadAs(inputStream, type);

            if (result == null) {
                throw new RuntimeException("Config file is not valid: " + yamlFilePath);
            }
        } catch (IOException e) {
            throw new RuntimeException("It's not possible to read the config file: " + yamlFilePath
                    + " \nmake sure the config file exists");
        }

        return result;
    }

    @Override
    public PluginConfig read(String configFilePath) throws IOException {
        return this.<PluginConfig>mapYamlToType(configFilePath, PluginConfig.class);
    }
}

