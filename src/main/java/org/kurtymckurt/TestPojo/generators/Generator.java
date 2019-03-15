package org.kurtymckurt.TestPojo.generators;

import org.kurtymckurt.TestPojo.limiters.Limiter;

import java.lang.reflect.Field;

/***
 * Interface that will generate the classes that it supports.
 *
 */
public interface Generator {
    Object generate(Class<?> clazz, Field field, Limiter limiter);
    boolean supportsType(Class<?> clazz);
}
