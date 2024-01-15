package com.twlabs.kinds.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.Set;
import org.junit.jupiter.api.Test;


public class KindFinderTest {

    @Test
    void test_get_all_by_reflections_should_return_at_least_one() {
        Set<Class<?>> allByReflections = KindFinder.getAllKindHandlersByReflections();
        assertNotNull(allByReflections);
        assertThat(allByReflections).hasSizeGreaterThan(0);
    }
}

