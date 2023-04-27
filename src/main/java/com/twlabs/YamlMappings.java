package com.twlabs;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.yaml.snakeyaml.Yaml;

/**
 * YamlMappings
 */
public class YamlMappings implements ConfigMappings {

    private List<Mapping> mappings;

    @Override
    public List<Mapping> getMappings() {
        return this.mappings;

    }

}
