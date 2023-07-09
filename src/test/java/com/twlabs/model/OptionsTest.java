package com.twlabs.model;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * OptionsTest
 */
public class OptionsTest {


    @Test
    public void test_valid_options() {

        String prefix = "_{{";
        String suffix = "}}_";
        Map<String, String> extensions = Map.of("k1",  "v1",  "k2", "v2");

        assertThat(new Options(prefix, suffix, extensions)).isNotNull()
                .extracting(Options::getPlaceholderPrefix, Options::getPlaceholderSuffix, Options::getExtensions)
                .containsExactly(prefix, suffix, extensions);
    }
}
