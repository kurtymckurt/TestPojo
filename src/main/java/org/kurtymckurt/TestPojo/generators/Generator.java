package org.kurtymckurt.TestPojo.generators;

import org.kurtymckurt.TestPojo.limiters.Limiter;

import java.lang.reflect.Field;

/***
 * Interface that will generate the classes that it supports.
 *
 */
public interface Generator<T> {

    /***
     * Generates an instance of this class with random test data
     * @param clazz class to create
     * @param field used to determine generic type (i.e. collections)
     * @param limiter used to limit the randomness of the data
     * @return
     */
    T generate(Class<?> clazz, Field field, Limiter limiter);

    /***
     * Returns true if this Generator can generate an instance of this class.
     * @param clazz class instance to check
     * @return true if it can create an instance of that class.
     */
    boolean supportsType(Class<?> clazz);
}
