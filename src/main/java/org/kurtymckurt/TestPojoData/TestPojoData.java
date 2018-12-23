package org.kurtymckurt.TestPojoData;

import org.kurtymckurt.TestPojoData.providers.ProviderFunction;

public class TestPojoData {

    public static <T> TestPojoDataBuilder<T> builder(Class<T> clazz) {
        return new TestPojoDataBuilder<>(clazz);
    }

    public static <T> TestPojoDataBuilder<T> builder(Class<T> clazz, ProviderFunction providerFunction) {
        return new TestPojoDataBuilder<>(clazz, providerFunction);
    }
}
