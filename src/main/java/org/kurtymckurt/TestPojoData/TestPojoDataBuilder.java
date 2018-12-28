package org.kurtymckurt.TestPojoData;

import org.kurtymckurt.TestPojoData.generators.Generator;
import org.kurtymckurt.TestPojoData.limiters.Limiter;
import org.kurtymckurt.TestPojoData.providers.Provider;
import org.kurtymckurt.TestPojoData.providers.ProviderFunction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestPojoDataBuilder<T> {

    private final Class<T> clazz;
    private final ProviderFunction providerFunction;
    private final List<Provider> providers;
    private final List<Generator> customGenerators;
    private final Map<String, Limiter> fieldToLimiter;

    public TestPojoDataBuilder(Class<T> clazz) {
        this(clazz, null);
    }

    public TestPojoDataBuilder(Class<T> clazz, ProviderFunction providerFunction) {
        this.clazz = clazz;
        this.providerFunction = providerFunction;
        this.customGenerators = new ArrayList<>();
        this.providers = new ArrayList<>();
        this.fieldToLimiter = new HashMap<>();
    }

    public TestPojoDataBuilder<T> addCustomGenerator(Generator generator) {
        customGenerators.add(generator);
        return this;
    }

    public TestPojoDataBuilder<T> addProvider(Provider provider) {
        providers.add(provider);
        return this;
    }

    public TestPojoDataBuilder<T> addLimiter(String fieldName, Limiter limiter) {
        fieldToLimiter.put(fieldName, limiter);
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
                        .limiters(fieldToLimiter)
                        .build())
                .buildObject();
    }
}
