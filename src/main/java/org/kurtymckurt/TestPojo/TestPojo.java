package org.kurtymckurt.TestPojo;

import org.kurtymckurt.TestPojo.providers.ProviderFunction;

public class TestPojo {

    public static <T> TestPojoBuilder<T> builder(Class<T> clazz) {
        return new TestPojoBuilder<>(clazz);
    }

    public static <T> TestPojoBuilder<T> builder(Class<T> clazz, ProviderFunction providerFunction) {
        return new TestPojoBuilder<>(clazz, providerFunction);
    }
}
