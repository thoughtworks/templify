package com.twlabs.services;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import com.twlabs.model.settings.PluginConfig;

/**
 * YamlConfigReaderTest
 */
public class YamlConfigReaderTest {


    @Test
    public void test_map_config_reader() throws IOException {

        YamlConfigReader reader = new YamlConfigReader();

        PluginConfig config = reader.read("src/test/resources/config/yaml/config.yml");

        assertThat(config).isNotNull();

        String kind = config.getSteps().get(0).getKind();
        String type = String.valueOf(config.getSteps().get(0).getMetadata().get("type"));
        assertThat(config.getSettings()).containsKey("prefix").containsKey("suffix");

        assertThat(kind).isEqualTo("FileHandler");
        assertThat(type).isEqualTo("xml");

    }
}
