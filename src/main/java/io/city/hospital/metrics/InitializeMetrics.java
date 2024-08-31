package io.city.hospital.metrics;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface InitializeMetrics {

    /**
     * Classes were the metrics name are present in the form of java constants
     */
    Class<?>[] classes() default {};
}
