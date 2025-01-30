package com.thoughtworks.kinds.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.thoughtworks.kinds.handlers.base.KindDefaultSpec;

/**
 * KindMetadata
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface KindHandler {
    public String name();

    public Class<?> specClass() default KindDefaultSpec.class;

    public String apiVersion() default "v1";

}
