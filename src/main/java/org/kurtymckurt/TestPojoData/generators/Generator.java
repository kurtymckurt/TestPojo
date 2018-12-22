package org.kurtymckurt.TestPojoData.generators;

import java.lang.reflect.Field;

/***
 * Interface that will generate the classes that it supports.
 *
 */
public interface Generator {
    Object generate(Class<?> clazz, Field field);
    boolean supportsType(Class<?> clazz);
}
