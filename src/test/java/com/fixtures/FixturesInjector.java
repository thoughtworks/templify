package com.fixtures;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * FixturesInjector
 */
@TestInstance(Lifecycle.PER_CLASS)
public class FixturesInjector {

    @BeforeAll
    public void inject() {
        Injector injector = Guice.createInjector(new ContextTestDependencyInjection());
        injector.injectMembers(this);
    }
}
