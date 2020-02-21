package org.kurtymckurt.TestPojo;

import lombok.extern.slf4j.Slf4j;
import org.kurtymckurt.TestPojo.exceptions.NoSuchFieldException;
import org.kurtymckurt.TestPojo.generators.Generator;
import org.kurtymckurt.TestPojo.generators.collections.*;
import org.kurtymckurt.TestPojo.generators.primatives.*;
import org.kurtymckurt.TestPojo.generators.time.*;
import org.kurtymckurt.TestPojo.limiters.Limiter;
import org.kurtymckurt.TestPojo.providers.ProviderFunction;
import org.kurtymckurt.TestPojo.util.LimiterUtils;
import org.kurtymckurt.TestPojo.util.NullSafeLimits;
import org.kurtymckurt.TestPojo.util.RandomUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/****
 * Actually does the logic of creating the pojo using reflection.
 */
@Slf4j
public class PojoBuilder<T> {

    private final List<Generator> generators;
    private final Map<Class, ProviderFunction> providerFunctions;
    private final Class<?> clazz;
    private final Map<Field, Limiter> limiters;
    private final Set<Field> excludedFields;
    private final PojoBuilderConfiguration configuration;

    public PojoBuilder(PojoBuilderConfiguration configuration) {
        this.clazz = configuration.getClazz();
        this.providerFunctions = new HashMap<>();
        this.providerFunctions.putAll(configuration.getProviderFunctions());
        this.limiters = new HashMap<>();
        this.generators = new ArrayList<>();
        this.excludedFields = new HashSet<>();
        this.configuration = configuration;

        verifyAndBuildExcludedFieldSet(configuration.getExcludedFields());

        verifyAndBuildLimitersMap(configuration.getLimiters());
        //Add the custom ones first in case they want
        //to override.
        this.generators.addAll(configuration.getGenerators());
        addCoreGenerators();

    }

    public PojoBuilderConfiguration getConfigurationWithOnlyProvidersAndGenerators() {
        return configuration.toBuilder().clearExcludedFields().clearLimiters().build();
    }

    private void verifyAndBuildExcludedFieldSet(Set<String> fieldsToExclude) {
        for(String field : fieldsToExclude) {
            String[] fieldNames = field.split("\\.");
            List<String> fieldNameList = Arrays.stream(fieldNames).collect(Collectors.toList());
            verifyAndBuildExcludesSetHelper(clazz, fieldNameList);
        }
    }

    private void verifyAndBuildExcludesSetHelper(Class<?> type, List<String> fieldNames) {
        try {
            Field declaredField = type.getDeclaredField(fieldNames.get(0));
            if(fieldNames.size() > 1) {
                fieldNames.remove(0);
                verifyAndBuildExcludesSetHelper(declaredField.getType(), fieldNames);
            } else {
                excludedFields.add(declaredField);
            }
        } catch (java.lang.NoSuchFieldException e) {
            throw new NoSuchFieldException(fieldNames.get(0), type, e);
        }
    }

    private void verifyAndBuildLimitersMap(Map<String, Limiter> limiters) {
        Set<Map.Entry<String, Limiter>> entries =  limiters.entrySet();
        for (Map.Entry<String, Limiter> entry : entries) {
            String[] fieldNames = entry.getKey().split("\\.");
            List<String> fieldNameList = Arrays.stream(fieldNames).collect(Collectors.toList());
            verifyAndBuildLimitersMapHelper(clazz, fieldNameList, entry.getValue());
        }
    }

    private void verifyAndBuildLimitersMapHelper(Class<?> type, List<String> fieldNames, Limiter limiter) {
        try {
            Field declaredField = type.getDeclaredField(fieldNames.get(0));
            if(fieldNames.size() > 1) {
                fieldNames.remove(0);
                verifyAndBuildLimitersMapHelper(declaredField.getType(), fieldNames, limiter);
            } else {
                limiters.put(declaredField, limiter);
            }
        } catch (java.lang.NoSuchFieldException e) {
            throw new NoSuchFieldException(fieldNames.get(0), type, e);
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

    public T buildObject() {
        return buildObject(clazz);
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

        try {

            //check if we have a provider function
            ProviderFunction providerFunction = providerFunctions.get(clazz);
            if(providerFunction != null) {
                instance = providerFunction.provide();
            }

            //If we didn't find a provider, try to new it up ourselves
            //this requires a no arg constructor...
            if(instance == null) {
                instance = clazz.newInstance();
            }

            log.debug("[*] created object {}.", instance);
            log.debug("[*] attempting to fill the object {}.", instance);
            instance = fillInstanceVariables(instance);
        } catch (InstantiationException e) {
            log.info("Problems instantiating the object", e);
        } catch (IllegalAccessException e) {
            log.info("Problems accessing properties on the object", e);
        }

        log.debug("[*] completed object: {}", instance);
        return (T) instance;
    }

    private Object fillInstanceVariables(Object instance) {
        Field[] declaredFields = instance.getClass().getDeclaredFields();
        if(declaredFields.length == 0) return instance;

        for(Field f : declaredFields) {
            if(excludedFields.contains(f)) {
                log.debug("[*] Skipping due to exclusion. field name: {}, field: {}.", f.getName(), f.getType());
                continue;
            }
            f.setAccessible(true);
            setField(instance, f);
        }
        return instance;
    }

    void setField(Object instance, Field f) {
        Class<?> type = f.getType();
        log.debug("[*] attempting to generate field name: {}, field: {}.", f.getName(), type);
        try {

            //Gotta try the primitives
            if (type.isAssignableFrom(Integer.TYPE)) {
                f.setInt(instance, new IntegerGenerator().generate(type, f, limiters.get(f), getConfigurationWithOnlyProvidersAndGenerators()));
            } else if (type.isAssignableFrom(Double.TYPE)) {
                f.setDouble(instance, new DoubleGenerator().generate(type, f, limiters.get(f), getConfigurationWithOnlyProvidersAndGenerators()));
            } else if (type.isAssignableFrom(Long.TYPE)) {
                f.setLong(instance, new LongGenerator().generate(type, f, limiters.get(f), getConfigurationWithOnlyProvidersAndGenerators()));
            } else if (type.isAssignableFrom(Float.TYPE)) {
                f.setFloat(instance, new FloatGenerator().generate(type, f, limiters.get(f), getConfigurationWithOnlyProvidersAndGenerators()));
            } else if (type.isAssignableFrom(Byte.TYPE)) {
                f.setByte(instance, RandomUtils.getRandomByte());
            } else if (type.isAssignableFrom(Short.TYPE)) {
                f.setShort(instance, new ShortGenerator().generate(type, f, limiters.get(f), getConfigurationWithOnlyProvidersAndGenerators()));
            } else if (type.isAssignableFrom(Boolean.TYPE)) {
                f.setBoolean(instance, RandomUtils.getRandomBoolean());
            } else if (type.isAssignableFrom(Character.TYPE)) {
                f.setChar(instance, RandomUtils.getRandomCharacter());
            }else if (type.isEnum()) {
                f.set(instance,
                        type.getEnumConstants()[
                                RandomUtils.getRandomIntWithinRange(type.getEnumConstants().length)]);
            } else if(type.isArray()) {
                Class<?> componentType = type.getComponentType();
                log.debug("[*] creating array of type: {}", componentType);
                Object arr = generateArray(componentType, limiters.get(f));
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
        } catch (IllegalAccessException e) {
            log.debug("[*] Exception trying to generate the field {}", type.getTypeName(), e);
        }
    }

    Object generateArray(Class<?> type, Limiter limiter) {
        NullSafeLimits nullSafeLimits = LimiterUtils.getNullSafeLimits(0, 10, limiter);
        int size = nullSafeLimits.length;
        Object arr = Array.newInstance(type, size);

        for(int i = 0; i < size; i++) {

            //Primitives
            if (type.isAssignableFrom(Integer.TYPE)) {
                Array.setInt(arr, i, new IntegerGenerator().generate(type, null, limiter, getConfigurationWithOnlyProvidersAndGenerators()));
            } else if (type.isAssignableFrom(Double.TYPE)) {
                Array.setDouble(arr, i, new DoubleGenerator().generate(type, null, limiter, getConfigurationWithOnlyProvidersAndGenerators()));
            } else if (type.isAssignableFrom(Float.TYPE)) {
                Array.setFloat(arr, i, new FloatGenerator().generate(type, null, limiter, getConfigurationWithOnlyProvidersAndGenerators()));
            } else if (type.isAssignableFrom(Long.TYPE)) {
                Array.setLong(arr, i, new LongGenerator().generate(type, null, limiter, getConfigurationWithOnlyProvidersAndGenerators()));
            } else if (type.isAssignableFrom(Short.TYPE)) {
                Array.setShort(arr, i, new ShortGenerator().generate(type, null, limiter, getConfigurationWithOnlyProvidersAndGenerators()));
            } else if (type.isAssignableFrom(Byte.TYPE)) {
                Array.setByte(arr, i, RandomUtils.getRandomByte());
            } else if (type.isAssignableFrom(Character.TYPE)) {
                Array.setChar(arr, i, RandomUtils.getRandomCharacter());
            } else if (type.isAssignableFrom(Boolean.TYPE)) {
                Array.setBoolean(arr, i, RandomUtils.getRandomBoolean());
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
