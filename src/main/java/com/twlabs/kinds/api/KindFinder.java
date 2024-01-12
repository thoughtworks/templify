package com.twlabs.kinds.api;

import java.util.Set;
import org.reflections.Reflections;

/**
 * KindFinder
 */
public abstract class KindFinder {

    public static Set<Class<?>> getAll() {
        Reflections reflections = new Reflections("com.twlabs.kinds");
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(KindHandler.class);
        return annotated;
    }

}
