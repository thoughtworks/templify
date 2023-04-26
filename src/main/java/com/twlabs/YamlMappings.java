package com.twlabs;

import java.util.ArrayList;
import java.util.List;

/**
 * YamlMappings
 */
public class YamlMappings implements ConfigMappings {

    private List<Mapping> mappings;

public YamlMappings(){
    this.mappings = new ArrayList<>();
    }


    @Override
    public List<Mapping> getMappings() {

        return this.mappings;

    }

    @Override
    public void setMappings(List<Mapping> mappings) {
        this.mappings = mappings;

    }



}
