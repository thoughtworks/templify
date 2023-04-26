package com.twlabs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(ReplaceUnderscores.class)
public class ConfigMappingsTest {

    @Test
    public void test_create_empty_ConfigMappings() {
        ConfigMappings actual = new YamlMappings();

        assertNotNull(actual.getMappings());
        assertEquals(0, actual.getMappings().size());

    }

}
