package com.twlabs.interfaces;

import com.twlabs.model.YamlMappings;

/**
 * ConfigReader
 */
public interface ConfigReader {

    public YamlMappings read(String configFilePath);

}
