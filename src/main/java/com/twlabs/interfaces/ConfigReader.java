package com.twlabs.interfaces;

import java.io.IOException;
import com.twlabs.model.settings.PluginConfig;


/**
 * Interface to read maven-cookiecutter.yaml|yml file. Apply relevant validations.
 *
 * Create a PluginConfig object that represents the values in maven-cookiecutter.yaml|yml
 *
 */
public interface ConfigReader {

    /**
     * Reads a plugin configuration file (maven-cookiecutter.yaml|yml) and returns a PluginConfig
     * object.
     *
     * @param configFilePath The path to the plugin configuration file.
     * @return The PluginConfig object representing the configuration file.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public PluginConfig read(String configFilePath) throws IOException;


}
