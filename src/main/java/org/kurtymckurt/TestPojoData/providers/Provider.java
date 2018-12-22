package org.kurtymckurt.TestPojoData.providers;

public interface Provider {
    boolean supportsType(Class<?> clazz);
    Object provide();
}
