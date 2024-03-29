package org.kurtymckurt.TestPojo;

import java.security.SecureRandom;
import lombok.extern.slf4j.Slf4j;
import org.kurtymckurt.TestPojo.generators.Generator;
import org.kurtymckurt.TestPojo.generators.PostGenerator;
import org.kurtymckurt.TestPojo.limiters.Limiter;
import org.kurtymckurt.TestPojo.providers.ProviderFunction;
import org.kurtymckurt.TestPojo.providers.ProviderFunctionContainer;
import org.kurtymckurt.TestPojo.util.RandomUtils;

import java.util.*;

@Slf4j
public class TestPojoBuilder<T> {

    private final Class<T> clazz;
    private final Map<Class, ProviderFunctionContainer> providerFunctions;
    private final List<Generator> customGenerators;
    private final Map<String, Map<String, PostGenerator>> postGenerators;
    private final Map<String, Limiter> fieldToLimiter;
    private final Set<String> excludedFields;
    private long seed;
    private boolean warnOnFieldNotExisting;

    public TestPojoBuilder(Class<T> clazz) {
        this(clazz, null);
    }

    public TestPojoBuilder(Class<T> clazz, ProviderFunction providerFunction) {
        this(clazz, null, providerFunction);
    }

    public TestPojoBuilder(Class<T> clazz, String builderMethod, ProviderFunction providerFunction) {
        this.clazz = clazz;
        this.customGenerators = new ArrayList<>();
        this.postGenerators = new HashMap<>();
        this.providerFunctions = new HashMap<>();
        addProviderFunction(providerFunction, builderMethod, clazz);
        this.fieldToLimiter = new HashMap<>();
        this.excludedFields = new HashSet<>();
        this.seed = new SecureRandom().nextLong();
        this.warnOnFieldNotExisting = false;
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
        return addProviderFunction(providerFunction, null, clazz, clazzes);
    }

    /***
     * Adds a custom provider in order to allow the builder to know how to create a *New* object of that type.
     * Requires at least one class for each provider.  If the provider creates for multiple classes, you can provide
     * more classes.  I.E ( a concurrent map creation can provide for ConcurrentMap, Map, etc)
     * @param providerFunction a provider that news an object of a provided type
     * @param builderMethod a method to call to build the class if its a builder
     * @param clazz a mandatory class that will be created by the provider function.
     * @param clazzes additional classes that can be created by provider function.
     * @return the builder
     */
    public TestPojoBuilder<T> addProviderFunction(ProviderFunction providerFunction, String builderMethod, Class<?> clazz, Class<?> ... clazzes) {

        providerFunctions.put(clazz, new ProviderFunctionContainer(providerFunction, builderMethod));

        if(clazzes != null) {
            for (Class<?> aClass : clazzes) {
                providerFunctions.put(aClass, new ProviderFunctionContainer(providerFunction, builderMethod));
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

    public TestPojoBuilder<T> addPostGenerator(String fieldTriggered, String fieldToSet, PostGenerator postGenerator) {
        Map<String, PostGenerator> stringPostGeneratorMap = this.postGenerators.get(fieldTriggered);
        if(stringPostGeneratorMap == null) {
            Map<String, PostGenerator> postGeneratorMap = new HashMap<>();
            postGeneratorMap.put(fieldToSet, postGenerator);
            this.postGenerators.put(fieldTriggered, postGeneratorMap);
        }
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

        if(fields != null) {
            for (String field : fields) {
                addExcludedField(field);
            }

        }
        return this;
    }

    public TestPojoBuilder<T> setRandomGeneratorSeed(long seed) {
        this.seed = seed;
        return this;
    }

    /**
     * Typically, if you set an excluded field or limiter, we will throw an exception to let you know that
     * the excludes or limiter isn't going to be used. Setting this to true, will enable only warning
     * via log message and will not throw an exception
     * @param warn if you want it to warn, otherwise error
     * @return TestPojoBuilder
     */
    public TestPojoBuilder<T> setWarnOnFieldNotExisting(boolean warn) {
        this.warnOnFieldNotExisting = warn;
        return this;
    }

    /***
     * Builds the object with the random data generated.
     *
     * @return T the object asked to build
     */
    public T build() {
        final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        log.info("Building [{}] using seed: [{}] from corresponding line {}:{}",
                clazz, seed, stackTrace[2].getFileName(),
                stackTrace[2].getLineNumber());
        return new PojoBuilder<T>(
                PojoBuilderConfiguration.builder()
                        .clazz(clazz)
                        .providerFunctions(providerFunctions)
                        .generators(customGenerators)
                        .postGenerators(postGenerators)
                        .limiters(fieldToLimiter)
                        .excludedFields(excludedFields)
                        .randomUtils(new RandomUtils(seed))
                        .warnOnFieldNotExisting(warnOnFieldNotExisting)
                        .build())
                .buildObject();
    }
}
