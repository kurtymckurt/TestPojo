package org.kurtymckurt.TestPojoData;

import lombok.Data;

@Data
public class TestPojoDataBuilder<T> {

    private Class<T> clazz;

    public TestPojoDataBuilder(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T build() {
        return PojoBuilder.buildObject(
                PojoBuilderDescriptor.<T>builder().clazz(clazz).build());
    }
}
