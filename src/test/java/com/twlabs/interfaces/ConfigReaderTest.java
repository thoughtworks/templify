package com.twlabs.interfaces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import com.twlabs.model.Mapping;
import com.twlabs.model.Placeholder;
import com.twlabs.model.PluginConfig;
import com.twlabs.services.YamlReader;

/**
 * Test interface {@link ConfigReader} by {@link YamlReader}
 */
@DisplayNameGeneration(ReplaceUnderscores.class)
public class ConfigReaderTest {

    final String test_yml = "src/test/resources/config/yaml/teste.yml";

    @Test
    public void test_confingMapping_is_builded() throws IOException {
        ConfigReader reader = new YamlReader();

        PluginConfig mappings = reader.read(test_yml);
        List<Mapping> actual = mappings.getMappings();

        assertThat(actual).isNotNull().isNotEmpty();

    }


    @ParameterizedTest
    @CsvSource({"0, pom.xml", "1, ./config/file.properties"})
    public void test_configMapping_getFile(int index, String expected) throws IOException {
        ConfigReader reader = new YamlReader();
        PluginConfig actual = reader.read(test_yml);

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
