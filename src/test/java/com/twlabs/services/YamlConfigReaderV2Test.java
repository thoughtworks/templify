package com.twlabs.services;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import com.twlabs.model.settings.PluginConfigV2;

/**
 * YamlConfigReaderV2Test
 */
public class YamlConfigReaderV2Test {


    @Test
    public void test_map_config_reader_v2() throws IOException {

        YamlConfigReaderV2 reader = new YamlConfigReaderV2();

        PluginConfigV2 config = reader.read("src/test/resources/config/yaml/configV2.yml");

        assertThat(config).isNotNull();

        String kind = config.getSteps().get(0).getKind();
        String type =  String.valueOf(config.getSteps().get(0).getMetadata().get("type"));

        assertThat(kind).isEqualTo("FileHandler");
        assertThat(type).isEqualTo("xml");

    }
}
