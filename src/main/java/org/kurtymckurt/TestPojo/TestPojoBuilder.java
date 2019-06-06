package org.kurtymckurt.TestPojo;

import org.kurtymckurt.TestPojo.generators.Generator;
import org.kurtymckurt.TestPojo.limiters.Limiter;
import org.kurtymckurt.TestPojo.providers.ProviderFunction;

import java.util.*;

public class TestPojoBuilder<T> {

    private final Class<T> clazz;
    private final ProviderFunction providerFunction;
    private final Map<Class, ProviderFunction> providerFunctions;
    private final List<Generator> customGenerators;
    private final Map<String, Limiter> fieldToLimiter;
    private final Set<String> excludedFields;

    public TestPojoBuilder(Class<T> clazz) {
        this(clazz, null);
    }

    public TestPojoBuilder(Class<T> clazz, ProviderFunction providerFunction) {
        this.clazz = clazz;
        this.providerFunction = providerFunction;
        this.customGenerators = new ArrayList<>();
        this.providerFunctions = new HashMap<>();
        addProviderFunction(providerFunction, clazz);
        this.fieldToLimiter = new HashMap<>();
        this.excludedFields = new HashSet<>();
    }

    public TestPojoBuilder<T> addGenerator(Generator generator) {
        customGenerators.add(generator);
        return this;
    }

    public TestPojoBuilder<T> addProviderFunction(ProviderFunction providerFunction, Class<?> ... clazzes) {
        for (Class<?> aClass : clazzes) {
            providerFunctions.put(aClass, providerFunction);
        }
        return this;
    }

    public TestPojoBuilder<T> addLimiter(String fieldName, Limiter limiter) {
        fieldToLimiter.put(fieldName, limiter);
        return this;
    }

    public TestPojoBuilder<T> addExcludedField(String fieldName) {
        this.excludedFields.add(fieldName);
        return this;
    }

    @SuppressWarnings(value="yeah, suppress this for now")
    public T build() {
        return (T) new PojoBuilder(
                PojoBuilderConfiguration.builder()
                        .clazz(clazz)
                        .providerFunctions(providerFunctions)
                        .generators(customGenerators)
                        .limiters(fieldToLimiter)
                        .excludedFields(excludedFields)
                        .build())
                .buildObject();
    }
}
