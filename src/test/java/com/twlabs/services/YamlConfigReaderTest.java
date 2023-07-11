package com.twlabs.services;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import com.twlabs.model.settings.PluginConfig;

/**
 * YamlConfigReaderTest
 */
@DisplayNameGeneration(ReplaceUnderscores.class)
public class YamlConfigReaderTest {

    @ParameterizedTest
    @CsvSource(value = {"src/test/resources/config/yaml/config.yml",
            "src/test/resources-its/com/twlabs/CookieCutterMojoIT/configuracao_basica_build_test/maven-cookiecutter.yml",
            "src/test/resources-its/com/twlabs/CookieCutterMojoIT/test_generic_java_project/maven-cookiecutter.yml",
            "src/test/resources-its/com/twlabs/CookieCutterMojoIT/test_json_handler_empty_pom/maven-cookiecutter.yml",
            "src/test/resources-its/com/twlabs/CookieCutterMojoIT/test_new_settings_v2/maven-cookiecutter.yml",
            "src/test/resources-its/com/twlabs/CookieCutterMojoIT/test_replace_default_pom_file/maven-cookiecutter.yml",
            "src/test/resources-its/com/twlabs/CookieCutterMojoIT/test_replace_generics_xml_files/maven-cookiecutter.yml",
            "src/test/resources-its/com/twlabs/CookieCutterMojoIT/test_replace_generics_yml_files/maven-cookiecutter.yml",
            "src/test/resources-its/com/twlabs/CookieCutterMojoIT/test_replace_java/maven-cookiecutter.yml",
            "src/test/resources-its/com/twlabs/CookieCutterMojoIT/test_replace_json_file/maven-cookiecutter.yml",
            "src/test/resources-its/com/twlabs/CookieCutterMojoIT/test_replace_throw_unsupported_file_type/maven-cookiecutter.yml",
            "src/test/resources-its/com/twlabs/CookieCutterMojoIT/test_running_with_existing_template_directory/maven-cookiecutter.yml",
            "src/test/resources-its/com/twlabs/CookieCutterMojoIT/test_using_custom_placeholder_settings/maven-cookiecutter.yml",
            "src/test/resources-its/com/twlabs/CookieCutterMojoIT/test_using_default_placeholder_settings/maven-cookiecutter.yml",})
    public void test_map_config_combinations(String configFile) throws IOException {

        YamlConfigReader reader = new YamlConfigReader();
        PluginConfig config = reader.read(configFile);

        assertThat(config).isNotNull();

        String kind = config.getSteps().get(0).getKind();
        String type = String.valueOf(config.getSteps().get(0).getMetadata().get("type"));

        assertThat(kind).isEqualTo("FileHandler");
        assertThat(type).isNotNull();

    }
}
