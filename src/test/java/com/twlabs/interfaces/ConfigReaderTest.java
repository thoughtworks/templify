package com.twlabs.interfaces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

    @Test
    public void test_confgFile_not_found() {
        ConfigReader reader = new YamlReader();

         assertThrows(RuntimeException.class,
         () -> reader.read("srt/test/resources/config/yaml/Not_Found.yml"));


    }


    @ParameterizedTest
    @CsvSource({"0, pom.xml", "1, ./config/file.properties"})
    public void test_configMapping_getFile(int index, String expected) throws IOException {
        ConfigReader reader = new YamlReader();
        PluginConfig actual = reader.read(test_yml);

        System.out.println(actual.getMappings().get(index).getFile());
        assertThat(actual.getMappings().get(index).getFile()).isEqualTo(expected);

    }


    @ParameterizedTest
    @CsvSource({"0, 0, /project/groupId, Cookiecutter.param.groupId", "1, 0, parametro1, valor1"})
    public void test_configMapping_getPlaceholder(int index, int indexPlaceHolder,
            String expectedQuery, String expectedName) throws IOException {
        ConfigReader reader = new YamlReader();
        PluginConfig config = reader.read(test_yml);

        List<Mapping> actual = config.getMappings();

        assertThat(actual.get(index).getPlaceholders().get(indexPlaceHolder).getQuery())
                .isEqualTo(expectedQuery);
        assertThat(actual.get(index).getPlaceholders().get(indexPlaceHolder).getName())
                .isEqualTo(expectedName);


    }


    //
    // public Mapping(String sourceFile, List<Placeholder> placeholders, String type, String
    // base_dir,
    @Test
    public void test_configMapping_with_type_baseid() {
        Placeholder placeholder = new Placeholder("source_key", "target_file");
        List<Placeholder> placeholderList = new ArrayList<>();

        placeholderList.add(placeholder);

        Mapping actual = new Mapping("source_file", placeholderList, "language_type",
                "src/main/java", "src/test/java");

        assertEquals(placeholderList, actual.getPlaceholders());
        assertNotNull(actual.getPlaceholders());
        assertEquals(placeholder, actual.getPlaceholders().get(0));
        assertEquals(actual.getFile(), "source_file");
        assertEquals(actual.getType(), "language_type");
        assertEquals(actual.getBase_dir(), "src/main/java");
        assertEquals(actual.getBase_test_dir(), "src/test/java");
    }



    @Test
    public void test_confingMapping_mapping_with_placeholder() {

        Placeholder placeholder = new Placeholder("source_key", "target_file");

        List<Placeholder> placeholderList = new ArrayList<>();

        placeholderList.add(placeholder);

        Mapping actual = new Mapping("source_file", placeholderList);

        assertEquals(placeholderList, actual.getPlaceholders());
        assertNotNull(actual.getPlaceholders());
        assertEquals(placeholder, actual.getPlaceholders().get(0));

    }


    @Test
    public void test_configMapping_placeholder() {
        Placeholder placeholder = new Placeholder("source_key", "target_value");
        assertEquals("source_key", placeholder.getQuery());
        assertEquals("target_value", placeholder.getName());
    }

}
