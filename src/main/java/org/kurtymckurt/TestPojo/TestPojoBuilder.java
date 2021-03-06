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

    /***
     * Adds a custom generator for a type that isn't currently supported out of the box.
     * This allows the builder to know how to build an object it otherwise wouldn't know how.
     *
     * For example, if there's a new type of collection interface that isn't currently supported.
     * @param generator generator for generating the new type of class.
     * @return the builder
     */
    public TestPojoBuilder<T> addGenerator(Generator generator) {
        customGenerators.add(generator);
        return this;
    }

    /***
     * Adds a custom provider in order to allow the builder to know how to create a *New* object of that type.
     * Requires at least one class for each provider.  If the provider creates for multiple classes, you can provide
     * more classes.  I.E ( a concurrent map creation can provide for ConcurrentMap, Map, etc)
     * @param providerFunction a provider that news an object of a provided type
     * @param clazz a mandatory class that will be created by the provider function.
     * @param clazzes additional classes that can be created by provider function.
     * @return the builder
     */
    public TestPojoBuilder<T> addProviderFunction(ProviderFunction providerFunction, Class<?> clazz, Class<?> ... clazzes) {

        providerFunctions.put(clazz, providerFunction);

        if(clazzes != null && clazzes.length > 0) {
            for (Class<?> aClass : clazzes) {
                providerFunctions.put(aClass, providerFunction);
            }
        }
        return this;
    }

    /***
     * Adds a new Limiter per field name that will limit and control the randomness of the generated field.
     * @param fieldName the name of the field
     * @param limiter the limiter for the field
     * @return the builder
     */
    public TestPojoBuilder<T> addLimiter(String fieldName, Limiter limiter) {
        fieldToLimiter.put(fieldName, limiter);
        return this;
    }

    /***
     * Adds a field name to the builder so that this field will not be generated.  This field
     * will likely be null but can depend on the constructor or builder used to provide the new object.
     *
     * You can also control this via a provider.
     * @param fieldName the field name
     * @return the builder
     */
    public TestPojoBuilder<T> addExcludedField(String fieldName) {
        this.excludedFields.add(fieldName);
        return this;
    }

    /***
     * Adds multiple fields to be excluded from generation.  This field will likely be null
     * but can depend on the constructor or builder used to provide the new object.
     *
     * You can also control this via a provider.
     * @param fieldName the field name to ignore
     * @param fields the additional field names to ignore
     * @return the builder
     */
    public TestPojoBuilder<T> addExcludedFields(String fieldName, String ... fields) {
        addExcludedField(fieldName);

        if(fields != null && fields.length > 0) {
            for (String field : fields) {
                addExcludedField(field);
            }

        }
        return this;
    }


    /***
     * Builds the object with the random data generated.
     *
     * @return T the object asked to build
     */
    public T build() {
        return new PojoBuilder<T>(
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
