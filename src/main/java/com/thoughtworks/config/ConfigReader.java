package com.thoughtworks.config;

import java.io.IOException;


/**
 * Interface to read maven-templify.yaml|yml file. Apply relevant validations.
 *
 * Create a PluginConfig object that represents the values in maven-templify.yaml|yml
 *
 */
public interface ConfigReader {

    /**
     * Reads a plugin configuration file (maven-templify.yaml|yml) and returns a PluginConfig
     * object.
     *
     * @param configFilePath The path to the plugin configuration file.
     * @return The PluginConfig object representing the configuration file.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public PluginConfig read(String configFilePath) throws IOException;


}
