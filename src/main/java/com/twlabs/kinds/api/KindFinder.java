package com.twlabs.kinds.api;

import java.util.Set;
import org.reflections.Reflections;

/**
 * KindFinder
 */
public abstract class KindFinder {

    private static final Reflections REFLECTIONS_TWLABS_KINDS = new Reflections("com.twlabs.kinds");

    public static Set<Class<?>> getAllKindHandlersByReflections() {
        return REFLECTIONS_TWLABS_KINDS.getTypesAnnotatedWith(KindHandler.class);
    }

}
