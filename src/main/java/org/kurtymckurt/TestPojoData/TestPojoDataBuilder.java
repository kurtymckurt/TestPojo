package org.kurtymckurt.TestPojoData;

import lombok.Data;
import org.kurtymckurt.TestPojoData.generators.Generator;

import java.util.ArrayList;
import java.util.List;

@Data
public class TestPojoDataBuilder<T> {

    private Class<T> clazz;
    private final List<Generator> customGenerators;

    public TestPojoDataBuilder(Class<T> clazz) {
        this.clazz = clazz;
        customGenerators = new ArrayList<>();
    }

    public void addCustomGenerator(Generator generator) {
        customGenerators.add(generator);
    }

    @SuppressWarnings(value="yeah, suppress this for now")
    public T build() {
        return (T) new PojoBuilder(
                PojoBuilderConfiguration.builder()
                        .clazz(clazz)
                        .generators(customGenerators)
                        .build())
                .buildObject();
    }
}
