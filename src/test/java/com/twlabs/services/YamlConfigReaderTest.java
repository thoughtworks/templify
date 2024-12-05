package com.twlabs.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import java.io.IOException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import com.twlabs.config.PluginConfig;

/**
 * YamlConfigReaderTest
 */
@DisplayNameGeneration(ReplaceUnderscores.class)
public class YamlConfigReaderTest {

    @ParameterizedTest
    @CsvSource(value = {
            "src/test/resources/config/yaml/config.yml",
            "src/test/resources-its/com/twlabs/mojos/TemplifyIT/test_basic_usage_example/maven-cookiecutter.yml",
            "src/test/resources-its/com/twlabs/mojos/TemplifyIT/java_project_example/maven-cookiecutter.yml",
            "src/test/resources-its/com/twlabs/mojos/TemplifyIT/basic_json_example/maven-cookiecutter.yml",
            "src/test/resources-its/com/twlabs/mojos/TemplifyIT/using_metadata_example/maven-cookiecutter.yml",
            "src/test/resources-its/com/twlabs/mojos/TemplifyIT/basic_default_options_example/maven-cookiecutter.yml",
            "src/test/resources-its/com/twlabs/mojos/TemplifyIT/basic_xml_example/maven-cookiecutter.yml",
            "src/test/resources-its/com/twlabs/mojos/TemplifyIT/basic_yml_example/maven-cookiecutter.yml",
            "src/test/resources-its/com/twlabs/mojos/TemplifyIT/basic_java_example/maven-cookiecutter.yml",
            "src/test/resources-its/com/twlabs/mojos/TemplifyIT/test_replace_json_file/maven-cookiecutter.yml",
            "src/test/resources-its/com/twlabs/mojos/TemplifyIT/test_replace_throw_unsupported_file_type/maven-cookiecutter.yml",
            "src/test/resources-its/com/twlabs/mojos/TemplifyIT/test_running_with_existing_template_directory/maven-cookiecutter.yml",
            "src/test/resources-its/com/twlabs/mojos/TemplifyIT/test_using_custom_placeholder_settings/maven-cookiecutter.yml",
            "src/test/resources-its/com/twlabs/mojos/TemplifyIT/test_using_default_placeholder_settings/maven-cookiecutter.yml"
    })
    public void test_map_config_combinations(String configFile) throws IOException {

        YamlConfigReader reader = new YamlConfigReader();
        assertThatNoException().isThrownBy(() -> reader.read(configFile));

        PluginConfig config = reader.read(configFile);

        assertThat(config).isNotNull();

        String kind = config.getSteps().get(0).getKind();
        String apiVersion = config.getSteps().get(0).getApiVersion();

        assertThat(kind).isNotNull();
        assertThat(apiVersion).isNotNull();

    }
}
