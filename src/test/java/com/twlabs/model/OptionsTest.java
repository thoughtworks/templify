package com.twlabs.model;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

/**
 * OptionsTest
 */
public class OptionsTest {


    @Test
    public void test_valid_options() {

        String prefix = "_{{";
        String suffix = "}}_";

        assertThat(new Options(prefix, suffix)).isNotNull()
                .extracting(Options::getPlaceholderPrefix, Options::getPlaceholderSuffix)
                .containsExactly(prefix, suffix);
    }
}
