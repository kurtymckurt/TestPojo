package org.kurtymckurt.TestPojo;

import lombok.extern.slf4j.Slf4j;
import org.kurtymckurt.TestPojo.exceptions.IllegalAccessException;
import org.kurtymckurt.TestPojo.exceptions.InstantiationException;
import org.kurtymckurt.TestPojo.exceptions.NoSuchFieldException;
import org.kurtymckurt.TestPojo.exceptions.NoSuchMethodException;
import org.kurtymckurt.TestPojo.generators.Generator;
import org.kurtymckurt.TestPojo.generators.PostGenerator;
import org.kurtymckurt.TestPojo.generators.collections.*;
import org.kurtymckurt.TestPojo.generators.primatives.*;
import org.kurtymckurt.TestPojo.generators.time.*;
import org.kurtymckurt.TestPojo.limiters.ArrayLimiter;
import org.kurtymckurt.TestPojo.limiters.Limiter;
import org.kurtymckurt.TestPojo.providers.ProviderFunction;
import org.kurtymckurt.TestPojo.providers.ProviderFunctionContainer;
import org.kurtymckurt.TestPojo.util.ArrayNullSafeLimits;
import org.kurtymckurt.TestPojo.util.LimiterUtils;
import org.kurtymckurt.TestPojo.util.RandomUtils;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

/****
 * Actually does the logic of creating the pojo using reflection.
 */
@Slf4j
public class PojoBuilder<T> {

    private final List<Generator> generators;
    private final Map<Class, ProviderFunctionContainer> providerFunctions;
    private final Class<?> clazz;
    private final Map<Field, Limiter> limiters;
    private final Map<Class, Set<Field>> excludedFields;
    private final Set<Field> excludedPostGeneratorFields;
    private final Map<Field, Map<Field, PostGenerator>> postGenerators;
    private final PojoBuilderConfiguration configuration;
    private final RandomUtils randomUtils;
    private final boolean warnOnFieldNotExisting;  // for excluding/limiting fields

    private static final IntegerGenerator INTEGER_GENERATOR = new IntegerGenerator();
    private static final ShortGenerator SHORT_GENERATOR = new ShortGenerator();
    private static final FloatGenerator FLOAT_GENERATOR = new FloatGenerator();
    private static final DoubleGenerator DOUBLE_GENERATOR = new DoubleGenerator();
    private static final LongGenerator LONG_GENERATOR = new LongGenerator();

    public PojoBuilder(PojoBuilderConfiguration configuration) {
        this.clazz = configuration.getClazz();
        this.providerFunctions = new HashMap<>();
        this.providerFunctions.putAll(configuration.getProviderFunctions());
        this.limiters = new HashMap<>();
        this.postGenerators = new HashMap<>();
        this.excludedPostGeneratorFields = new HashSet<>();
        this.generators = new ArrayList<>();
        this.excludedFields = new HashMap<>();
        this.configuration = configuration;
        this.randomUtils = configuration.getRandomUtils();
        this.warnOnFieldNotExisting = configuration.isWarnOnFieldNotExisting();

        verifyAndBuildExcludedFieldSet(clazz, configuration.getExcludedFields());
        verifyAndBuildLimitersMap(clazz, configuration.getLimiters());
        verifyAndBuildPostGeneratorsFieldSet(clazz, configuration.getPostGenerators());
        //Add the custom ones first in case they want
        //to override.
        this.generators.addAll(configuration.getGenerators());
        addCoreGenerators();

    }

    public PojoBuilderConfiguration getConfigurationWithOnlyProvidersAndGenerators() {
        return configuration.toBuilder().clearExcludedFields().clearLimiters().build();
    }

    public T buildObject() {
        return buildObject(clazz);
    }

    private void verifyAndBuildExcludedFieldSet(Class clazz, Set<String> fieldsToExclude) {
        for(String field : fieldsToExclude) {
            String[] fieldNames = field.split("\\.");
            List<String> fieldNameList = Arrays.stream(fieldNames).collect(Collectors.toList());
            verifyAndBuildExcludesSetHelper(clazz, fieldNameList);
        }
    }

    private void verifyAndBuildExcludesSetHelper(Class<?> type, List<String> fieldNames) {
        Class<?> typeToUse = type.isArray() ? type.getComponentType() : type;
        try {
            Field declaredField = typeToUse.getDeclaredField(fieldNames.get(0));
            if(fieldNames.size() > 1) {
                fieldNames.remove(0);
                verifyAndBuildExcludesSetHelper(declaredField.getType(), fieldNames);
            } else {
                Set<Field> fields = excludedFields.get(typeToUse);
                if(fields == null) {
                    fields = new HashSet<>();
                }
                fields.add(declaredField);
                excludedFields.put(typeToUse, fields);
            }
        } catch (java.lang.NoSuchFieldException e) {
            if(!warnOnFieldNotExisting) {
                throw new NoSuchFieldException(fieldNames.get(0), typeToUse, e);
            } else {
                log.warn("Could not find field: {} of type: {} for provided excludes.", fieldNames.get(0), typeToUse);
            }
        }
    }

    private void verifyAndBuildLimitersMap(Class clazz, Map<String, Limiter> limiters) {
        Set<Map.Entry<String, Limiter>> entries =  limiters.entrySet();
        for (Map.Entry<String, Limiter> entry : entries) {
            String[] fieldNames = entry.getKey().split("\\.");
            List<String> fieldNameList = Arrays.stream(fieldNames).collect(Collectors.toList());
            verifyAndBuildLimitersMapHelper(clazz, fieldNameList, entry.getValue());
        }
    }

    private void verifyAndBuildLimitersMapHelper(Class<?> type, List<String> fieldNames, Limiter limiter) {
        Class<?> typeToUse = type.isArray() ? type.getComponentType() : type;
        try {
            Field declaredField = typeToUse.getDeclaredField(fieldNames.get(0));
            if(fieldNames.size() > 1) {
                fieldNames.remove(0);
                verifyAndBuildLimitersMapHelper(declaredField.getType(), fieldNames, limiter);
            } else {
                limiters.put(declaredField, limiter);
            }
        } catch (java.lang.NoSuchFieldException e) {
            if(!warnOnFieldNotExisting) {
                throw new NoSuchFieldException(fieldNames.get(0), typeToUse, e);
            }else {
                log.warn("Could not find field: {} of type: {} for provided limiter.", fieldNames.get(0), typeToUse);
            }
        }
    }

    private void verifyAndBuildPostGeneratorsFieldSet(Class clazz, Map<String, Map<String, PostGenerator>> postGenerators) {
        for(Map.Entry<String, Map<String, PostGenerator>> entry : postGenerators.entrySet()) {
            String[] toTriggerFieldNames = entry.getKey().split("\\.");
            List<String> toTriggerFieldNameList = Arrays.stream(toTriggerFieldNames).collect(Collectors.toList());
            Field toTriggerField = getFieldByString(clazz, toTriggerFieldNameList);

            Map<String, PostGenerator> value = entry.getValue();
            for (Map.Entry<String, PostGenerator> stringPostGeneratorEntry : value.entrySet()) {
                String[] toSetFieldNames = stringPostGeneratorEntry.getKey().split("\\.");
                List<String> toSetFieldNameList = Arrays.stream(toSetFieldNames).collect(Collectors.toList());
                verifyAndBuildPostGeneratorsFieldSetHelper(clazz,
                        toSetFieldNameList,
                        toTriggerField,
                        stringPostGeneratorEntry.getValue());
            }
        }
    }

    private Field getFieldByString(Class<?> type, List<String> fieldNames) {
        Class<?> typeToUse = type.isArray() ? type.getComponentType() : type;
        try {
            Field declaredField = typeToUse.getDeclaredField(fieldNames.get(0));
            if(fieldNames.size() > 1) {
                fieldNames.remove(0);
                return getFieldByString(declaredField.getType(), fieldNames);
            } else {
                return declaredField;
            }
        } catch (java.lang.NoSuchFieldException e) {
            if(!warnOnFieldNotExisting) {
                throw new NoSuchFieldException(fieldNames.get(0), typeToUse, e);
            }
        }
        if(!warnOnFieldNotExisting) {
            throw new NoSuchFieldException(fieldNames.get(0), typeToUse, null);
        }
        return null;
    }

    private void verifyAndBuildPostGeneratorsFieldSetHelper(Class<?> type,
                                                            List<String> fieldNames,
                                                            Field toTriggerField,
                                                            PostGenerator postGenerator) {
        Class<?> typeToUse = type.isArray() ? type.getComponentType() : type;
        try {
            Field declaredField = typeToUse.getDeclaredField(fieldNames.get(0));
            if(fieldNames.size() > 1) {
                fieldNames.remove(0);
                verifyAndBuildPostGeneratorsFieldSetHelper(declaredField.getType(), fieldNames, toTriggerField, postGenerator);
            } else {
                Map<Field, PostGenerator> fieldPostGeneratorMap = postGenerators.get(toTriggerField);
                if(fieldPostGeneratorMap == null) {
                    Map<Field, PostGenerator> fieldPostGeneratorMap1 = new HashMap<>();
                    fieldPostGeneratorMap1.put(declaredField, postGenerator);
                    this.postGenerators.put(toTriggerField, fieldPostGeneratorMap1);
                } else {
                    fieldPostGeneratorMap.put(declaredField, postGenerator);
                }
                this.excludedPostGeneratorFields.add(declaredField);
            }
        } catch (java.lang.NoSuchFieldException e) {
            if(!warnOnFieldNotExisting) {
                throw new NoSuchFieldException(fieldNames.get(0), typeToUse, e);
            } else {
                log.warn("Could not find field: {} of type: {} for provided post generator.", fieldNames.get(0), typeToUse);
            }
        }
    }

    private void addCoreGenerators() {

        //Primitive Objects
        generators.add(new IntegerGenerator());
        generators.add(new LongGenerator());
        generators.add(new DoubleGenerator());
        generators.add(new FloatGenerator());
        generators.add(new ByteGenerator());
        generators.add(new BooleanGenerator());
        generators.add(new StringGenerator());
        generators.add(new ShortGenerator());
        generators.add(new CharacterGenerator());

        //Collections
        generators.add(new SetGenerator());
        generators.add(new ListGenerator());
        generators.add(new MapGenerator());
        generators.add(new NavigableSetGenerator());
        generators.add(new NavigableMapGenerator());
        generators.add(new QueueGenerator());
        generators.add(new BlockingDequeGenerator());

        // Date
        generators.add(new DateGenerator());

        // java.time
        generators.add(new LocalDateTimeGenerator());
        generators.add(new LocalDateGenerator());
        generators.add(new ZonedDateTimeGenerator());
        generators.add(new InstantGenerator());
        generators.add(new OffsetDateTimeGenerator());
    }

    private T buildObject(Class<?> clazz) {
        //First see if the object is just one we can generate from
        //our generators.
        for(Generator generator : generators) {
            if(generator.supportsType(clazz)){
                return (T) generator.generate(clazz, null, null, getConfigurationWithOnlyProvidersAndGenerators());
            }
        }

        log.debug("[*] creating object {}.", clazz);
        Object instance = null;
        String builderMethod = null;
        try {
            //check if we have a provider function
            ProviderFunctionContainer providerFunctionContainer = providerFunctions.get(clazz);
            if(providerFunctionContainer != null) {
                ProviderFunction providerFunction = providerFunctionContainer.getProviderFunction();
                if(providerFunction  != null) {
                    instance = providerFunction.provide();
                }
            }

            //If we didn't find a provider, try to new it up ourselves
            //this requires a no arg constructor...
            if(instance == null) {
                instance =  clazz.getDeclaredConstructor().newInstance();
            }

            log.debug("[*] created object {}.", instance);
            log.debug("[*] attempting to fill the object {}.", instance);
            fillInstanceVariables(instance);


            if(providerFunctionContainer != null) {
                builderMethod = providerFunctionContainer.getBuilderMethod();
                if(builderMethod != null) {
                    Method method = instance.getClass().getMethod(builderMethod, null);
                    instance = method.invoke(instance);
                }
            }
        } catch (java.lang.InstantiationException e) {
            log.info("Problems instantiating the object", e);
            throw new InstantiationException(clazz, e);
        } catch (java.lang.IllegalAccessException | InvocationTargetException e) {
            log.info("Problems accessing properties on the object", e);
            throw new IllegalAccessException(clazz, e);
        } catch (java.lang.NoSuchMethodException e) {
            log.info("Problems building builder on the object", e);
            throw new NoSuchMethodException(builderMethod, instance.getClass(), e);
        }

        log.debug("[*] completed object: {}", instance);

        return (T) instance;
    }

    private void fillInstanceVariables(Object instance) {
        Field[] declaredFields = instance.getClass().getDeclaredFields();
        if(declaredFields.length == 0) return;

        Set<Field> excludedForThisInstance = excludedFields.get(instance.getClass());

        for(Field f : declaredFields) {
            if(Modifier.isStatic(f.getModifiers()) &&
               Modifier.isFinal(f.getModifiers())) {
                log.debug("[*] Skipping due to field being static final. field name: {}, field: {}.",
                        f.getName(), f.getType());
                continue;
            }
            else if(excludedForThisInstance != null && excludedForThisInstance.contains(f)) {
                log.debug("[*] Skipping due to exclusion. field name: {}, field: {}.", f.getName(), f.getType());
                continue;
            } else if(excludedPostGeneratorFields.contains(f)) {
                log.debug("[*] Skipping due to post generator. field name: {}, field: {}.", f.getName(), f.getType());
                continue;
            }
            f.setAccessible(true);
            setField(instance, f);

            //Now we do post generators!
            executePostGenerators(instance, f);
        }
    }

    private void executePostGenerators(Object instance, Field f) {
        Map<Field, PostGenerator> fieldPostGeneratorMap = this.postGenerators.get(f);
        if(fieldPostGeneratorMap != null && !fieldPostGeneratorMap.isEmpty()) {
            for (Map.Entry<Field, PostGenerator> fieldPostGeneratorEntry : fieldPostGeneratorMap.entrySet()) {
                Field key = fieldPostGeneratorEntry.getKey();
                PostGenerator postGenerator = fieldPostGeneratorEntry.getValue();
                try {
                    key.setAccessible(true);
                    key.set(instance, postGenerator.generate(f.get(instance)));
                } catch (java.lang.IllegalAccessException e) {
                    throw new IllegalAccessException(instance.getClass(), e);
                }
            }
        }
    }

    void setField(Object instance, Field f) {
        final Class<?> type = f.getType();
        log.debug("[*] attempting to generate field name: {}, field: {}.", f.getName(), type);
        try {

            //Gotta try the primitives
            if (type.isAssignableFrom(Integer.TYPE)) {
                f.setInt(instance, INTEGER_GENERATOR.generate(type, f, limiters.get(f), getConfigurationWithOnlyProvidersAndGenerators()));
            } else if (type.isAssignableFrom(Double.TYPE)) {
                f.setDouble(instance, DOUBLE_GENERATOR.generate(type, f, limiters.get(f), getConfigurationWithOnlyProvidersAndGenerators()));
            } else if (type.isAssignableFrom(Long.TYPE)) {
                f.setLong(instance, LONG_GENERATOR.generate(type, f, limiters.get(f), getConfigurationWithOnlyProvidersAndGenerators()));
            } else if (type.isAssignableFrom(Float.TYPE)) {
                f.setFloat(instance, FLOAT_GENERATOR.generate(type, f, limiters.get(f), getConfigurationWithOnlyProvidersAndGenerators()));
            } else if (type.isAssignableFrom(Byte.TYPE)) {
                f.setByte(instance, randomUtils.getRandomByte());
            } else if (type.isAssignableFrom(Short.TYPE)) {
                f.setShort(instance, SHORT_GENERATOR.generate(type, f, limiters.get(f), getConfigurationWithOnlyProvidersAndGenerators()));
            } else if (type.isAssignableFrom(Boolean.TYPE)) {
                f.setBoolean(instance, randomUtils.getRandomBoolean());
            } else if (type.isAssignableFrom(Character.TYPE)) {
                f.setChar(instance, randomUtils.getRandomCharacter());
            }else if (type.isEnum()) {
                f.set(instance,
                        type.getEnumConstants()[
                                randomUtils.getRandomIntWithinRange(type.getEnumConstants().length)]);
            } else if(type.isArray()) {
                final Class<?> componentType = type.getComponentType();
                log.debug("[*] creating array of type: {}", componentType);
                final Limiter limiter = limiters.get(f);
                Object arr = generateArray(componentType, LimiterUtils.getArrayLimiter(limiter));
                f.set(instance, arr);
            } else {

                boolean generated = false;
                //Try the generators
                for(Generator generator : generators) {
                    if(generator.supportsType(type)) {
                        generated = true;
                        f.set(instance, generator.generate(type, f, limiters.get(f), getConfigurationWithOnlyProvidersAndGenerators()));
                        break;
                    }
                }

                if(!generated) {
                    //try a custom object
                    f.set(instance, buildObject(type));
                }
            }
        } catch (java.lang.IllegalAccessException e) {
            log.debug("[*] Exception trying to generate the field {}", type.getTypeName(), e);
        }
    }

    Object generateArray(Class<?> type, ArrayLimiter limiter) {
        ArrayNullSafeLimits nullSafeLimits = LimiterUtils.getArrayNullSafeLimits(
                0, 10, limiter, configuration.getRandomUtils());
        final int size = nullSafeLimits.length;
        final Object arr = Array.newInstance(type, size);

        for(int i = 0; i < size; i++) {

            //Primitives
            if (type.isAssignableFrom(Integer.TYPE)) {
                Array.setInt(arr, i, INTEGER_GENERATOR.generate(type, null, LimiterUtils.fromArrayLimiter(limiter)
                        , getConfigurationWithOnlyProvidersAndGenerators()));
            } else if (type.isAssignableFrom(Double.TYPE)) {
                Array.setDouble(arr, i, DOUBLE_GENERATOR.generate(type, null, LimiterUtils.fromArrayLimiter(limiter), getConfigurationWithOnlyProvidersAndGenerators()));
            } else if (type.isAssignableFrom(Float.TYPE)) {
                Array.setFloat(arr, i, FLOAT_GENERATOR.generate(type, null, LimiterUtils.fromArrayLimiter(limiter), getConfigurationWithOnlyProvidersAndGenerators()));
            } else if (type.isAssignableFrom(Long.TYPE)) {
                Array.setLong(arr, i, LONG_GENERATOR.generate(type, null, LimiterUtils.fromArrayLimiter(limiter), getConfigurationWithOnlyProvidersAndGenerators()));
            } else if (type.isAssignableFrom(Short.TYPE)) {
                Array.setShort(arr, i, SHORT_GENERATOR.generate(type, null, LimiterUtils.fromArrayLimiter(limiter), getConfigurationWithOnlyProvidersAndGenerators()));
            } else if (type.isAssignableFrom(Byte.TYPE)) {
                Array.setByte(arr, i, randomUtils.getRandomByte());
            } else if (type.isAssignableFrom(Character.TYPE)) {
                Array.setChar(arr, i, randomUtils.getRandomCharacter());
            } else if (type.isAssignableFrom(Boolean.TYPE)) {
                Array.setBoolean(arr, i, randomUtils.getRandomBoolean());
            } else {
                boolean generated = false;
                for(Generator generator : generators) {
                    if(generator.supportsType(type)) {
                        generated = true;
                        Array.set(arr, i, generator.generate(type, null, limiter, getConfigurationWithOnlyProvidersAndGenerators()));
                    }
                }

                if(!generated) {
                    //Try a custom object
                    Array.set(arr, i, buildObject(type));
                }
            }
        }

        return arr;
    }

}
