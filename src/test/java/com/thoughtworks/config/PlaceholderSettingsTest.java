package com.thoughtworks.config;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * PlaceholderSettingsTest
 */
public class PlaceholderSettingsTest {

    @Test
    public void test_PlaceholderSettings_is_empty() throws Exception {
        PlaceholderSettings settings = new PlaceholderSettings();
        assertTrue(settings.isEmpty());
    }

    private static Stream<Arguments> provideStringsForIsEmptyShouldByTrue() {
        return Stream.of(
                Arguments.of("not empty", (String) null),
                Arguments.of("not empty", ""),
                Arguments.of("not empty", "    "),
                Arguments.of((String) null, "not empty"),
                Arguments.of("", "not empty"),
                Arguments.of("    ", "not empty"));
    }

    @ParameterizedTest
    @MethodSource("provideStringsForIsEmptyShouldByTrue")
    public void test_PlaceholderSettings_is_not_empty_should_be_true(String prefix, String suffix)
            throws Exception {
        PlaceholderSettings settings = new PlaceholderSettings(prefix, suffix);
        assertTrue(settings.isEmpty());
    }

    private static Stream<Arguments> provideStringsForIsEmptyShouldByFalse() {
        return Stream.of(
                Arguments.of("not empty", "not empty"),
                Arguments.of(" not empty  ", "not empty"),
                Arguments.of("not empty", " not empty  "));
    }

    @ParameterizedTest
    @MethodSource("provideStringsForIsEmptyShouldByFalse")
    public void test_PlaceholderSettings_is_not_empty_should_be_false(String prefix, String suffix)
            throws Exception {
        PlaceholderSettings settings = new PlaceholderSettings(prefix, suffix);
        assertFalse(settings.isEmpty());
    }


}
