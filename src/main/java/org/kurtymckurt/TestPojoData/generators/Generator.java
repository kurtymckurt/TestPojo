package org.kurtymckurt.TestPojoData.generators;

public interface Generator {
    Object generate();
    boolean supportsType(Class<?> clazz);
}
