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
        Map<String, String> extendedOptions = Map.of("k1", "v1", "k2", "v2");

        assertThat(new Metadata(prefix, suffix, extendedOptions))
                .isNotNull().extracting(Metadata::getPrefix,
                        Metadata::getSuffix, Metadata::getExtended)
                .containsExactly(prefix, suffix, extendedOptions);
    }
}
