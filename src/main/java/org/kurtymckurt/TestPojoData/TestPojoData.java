package org.kurtymckurt.TestPojoData;

public class TestPojoData {

    public static <T> TestPojoDataBuilder<T> builder(Class<T> clazz) {
        return new TestPojoDataBuilder<>(clazz);
    }
}
