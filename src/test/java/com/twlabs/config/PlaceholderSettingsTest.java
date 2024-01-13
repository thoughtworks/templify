package com.twlabs.config;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * PlaceholderSettingsTest
 */
public class PlaceholderSettingsTest {

    @Test
    public void test_PlaceholderSettings_is_empty() throws Exception {

        PlaceholderSettings settings = new PlaceholderSettings();
        assertTrue(settings.isEmpty());
    }


    @Test
    public void test_PlaceholderSettings_is_not_empty() throws Exception {

        PlaceholderSettings settings = new PlaceholderSettings("foo", "bar");
        assertFalse(settings.isEmpty());
    }
}
