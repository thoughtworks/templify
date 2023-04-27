package com.twlabs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(ReplaceUnderscores.class)
public class ConfigMappingsTest {

    ConfigMappings mappings = new YamlMappings();


    final String test_yml = "config/yaml/teste.yml";



    @Test
    public void test_confingMapping_is_builded() {
        ConfigReader reader = new YamlReader();

        ConfigMappings mappings = reader.read(test_yml);
        List<Mapping> actual = mappings.getMappings();

        assertThat(actual).isNotNull().isNotEmpty();

    }


    @ParameterizedTest
    @CsvSource({"0, pom.xml",
            "1, ./config/file.properties"})
    public void test_configMapping_getFile(int index, String expected) {
        ConfigReader reader = new YamlReader();
        ConfigMappings actual = reader.read(test_yml);

        System.out.println(actual.getMappings().get(index).getFile());
        assertThat(actual.getMappings().get(index).getFile()).isEqualTo(expected);

    }



    @Test
    public void test_confingMapping_mapping_with_translation() {

        Placeholder placeholder = new Placeholder("source_key", "target_file");

        List<Placeholder> placeholderList = new ArrayList<>();

        placeholderList.add(placeholder);

        Mapping actual = new Mapping("source_file", placeholderList);

        assertEquals(placeholderList, actual.getPlaceholders());
        assertNotNull(actual.getPlaceholders());
        assertEquals(placeholder, actual.getPlaceholders().get(0));

    }


    @Test
    public void test_configMapping_translation() {
        Placeholder placeholder = new Placeholder("source_key", "target_value");
        assertEquals("source_key", placeholder.getQuery());
        assertEquals("target_value", placeholder.getName());
    }

}
