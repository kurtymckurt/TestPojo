package org.kurtymckurt.TestPojoData.generators;

import org.kurtymckurt.TestPojoData.limiters.Limiter;

import java.lang.reflect.Field;

/***
 * Interface that will generate the classes that it supports.
 *
 */
public interface Generator {
    Object generate(Class<?> clazz, Field field, Limiter limiter);
    boolean supportsType(Class<?> clazz);
}
