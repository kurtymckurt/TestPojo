package org.kurtymckurt.TestPojoData;

import lombok.Data;
import org.kurtymckurt.TestPojoData.generators.Generator;
import org.kurtymckurt.TestPojoData.providers.Provider;
import org.kurtymckurt.TestPojoData.providers.ProviderFunction;

import java.util.ArrayList;
import java.util.List;

@Data
public class TestPojoDataBuilder<T> {

    private final Class<T> clazz;
    private final ProviderFunction providerFunction;
    private final List<Provider> providers;
    private final List<Generator> customGenerators;

    public TestPojoDataBuilder(Class<T> clazz) {
        this.clazz = clazz;
        this.providerFunction = null;
        this.customGenerators = new ArrayList<>();
        providers = new ArrayList<>();
    }

    public TestPojoDataBuilder(Class<T> clazz, ProviderFunction providerFunction) {
        this.clazz = clazz;
        this.providerFunction = providerFunction;
        this.customGenerators = new ArrayList<>();
        this.providers = new ArrayList<>();
    }

    public TestPojoDataBuilder<T> addCustomGenerator(Generator generator) {
        customGenerators.add(generator);
        return this;
    }

    public TestPojoDataBuilder<T> addProvider(Provider provider) {
        providers.add(provider);
        return this;
    }

    @SuppressWarnings(value="yeah, suppress this for now")
    public T build() {
        return (T) new PojoBuilder(
                PojoBuilderConfiguration.builder()
                        .clazz(clazz)
                        .providerFunction(providerFunction)
                        .generators(customGenerators)
                        .providers(providers)
                        .build())
                .buildObject();
    }
}
