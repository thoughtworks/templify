package com.thoughtworks.kinds.handlers.base;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class KindPlaceholderTest {

    @Test
    public void test() {
        KindPlaceholder kindPlaceholder = new KindPlaceholder("match", "replace");
        assertEquals("match", kindPlaceholder.getMatch());
        assertEquals("replace", kindPlaceholder.getReplace());
    }
}
