package com.twlabs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import com.twlabs.ConfigMappings.Mapping;
import com.twlabs.ConfigMappings.Translation;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(ReplaceUnderscores.class)
public class ConfigMappingsTest {

    @Test
    public void test_create_empty_ConfigMappings() {
        ConfigMappings actual = new YamlMappings();

        assertNotNull(actual.getMappings());
        assertEquals(0, actual.getMappings().size());

    }


    @Test
    public void test_set_configMapping_mappings() {
        ConfigMappings actual = new YamlMappings();

        List<Mapping> mappingList = new ArrayList<>();
        Mapping mapping = new Mapping("source_file", "target_file", new ArrayList<>());
        mappingList.add(mapping);

        actual.setMappings(mappingList);

        assertEquals(1, actual.getMappings().size());
        assertEquals(mapping, actual.getMappings().get(0));

    }



    @Test
    public void test_confingMapping_mapping() {

        Mapping actual = new Mapping("source_file", "target_file", new ArrayList<>());

        assertEquals("source_file", actual.getSourceFile());
        assertEquals("target_file", actual.getTargetFile());
        assertTrue(actual.getTranslations().isEmpty());

    }



    @Test
    public void testTranslation() {
        Translation translation = new Translation("source_key", "target_value");
        assertEquals("source_key", translation.getSourceKey());
        assertEquals("target_value", translation.getTargetValue());
    }

}
