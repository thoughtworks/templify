package com.thoughtworks.kinds.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class KindFinderTest {

    @Test
    void test_get_all_registered_kinds() {
        Set<Kind<?>> allRegistedKinds = new KindFinder().getAllKindHandlers();
        assertNotNull(allRegistedKinds);
        assertThat(allRegistedKinds).hasSizeGreaterThan(0);
    }
}
