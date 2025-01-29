package com.thoughtworks.kinds.api;

import java.util.HashSet;
import java.util.Set;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import com.thoughtworks.di.KindHandlersModule;
import com.thoughtworks.kinds.handlers.KindHandlersIndex;

/**
 * KindFinder
 */
public class KindFinder {

    public Set<Kind<?>> getAllKindHandlers() {

        Injector injector = Guice.createInjector(new KindHandlersModule());

        Set<Kind<?>> kindHandlers = new HashSet<>();

        Set<String> registeredKindsNames = new KindHandlersIndex().getRegisteredKinds();

        for (String kindName : registeredKindsNames) {
            kindHandlers.add(injector.getInstance(Key.get(Kind.class, Names.named(kindName))));
        }

        return kindHandlers;

    }

}
