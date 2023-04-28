package com.twlabs.interfaces;

import java.io.IOException;
import com.twlabs.model.PluginConfig;

/**
 * ConfigReader
 */
public interface ConfigReader {

    public PluginConfig read(String configFilePath) throws IOException;

}
