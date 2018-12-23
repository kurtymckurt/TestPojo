package org.kurtymckurt.TestPojoData;

import lombok.extern.slf4j.Slf4j;
import org.kurtymckurt.TestPojoData.generators.Generator;
import org.kurtymckurt.TestPojoData.generators.collections.ListGenerator;
import org.kurtymckurt.TestPojoData.generators.collections.MapGenerator;
import org.kurtymckurt.TestPojoData.generators.collections.SetGenerator;
import org.kurtymckurt.TestPojoData.generators.primatives.*;
import org.kurtymckurt.TestPojoData.generators.time.*;
import org.kurtymckurt.TestPojoData.providers.Provider;
import org.kurtymckurt.TestPojoData.providers.ProviderFunction;
import org.kurtymckurt.TestPojoData.util.RandomUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/****
 * Actually does the logic of creating the pojo using reflection.
 */
@Slf4j
public class PojoBuilder {

    private final List<Generator> generators;
    private final List<Provider> providers;
    private final Class<?> clazz;
    private final ProviderFunction providerFunction;

    public PojoBuilder(PojoBuilderConfiguration configuration) {
        clazz = configuration.getClazz();
        providerFunction = configuration.getProviderFunction();
        generators = new ArrayList<>();
        providers = new ArrayList<>(configuration.getProviders());
        //Add the custom ones first in case they want
        //to override.
        generators.addAll(configuration.getGenerators());
        addCoreGenerators();
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

        // Date
        generators.add(new DateGenerator());

        // java.time
        generators.add(new LocalDateTimeGenerator());
        generators.add(new LocalDateGenerator());
        generators.add(new ZonedDateTimeGenerator());
        generators.add(new InstantGenerator());
        generators.add(new OffsetDateTimeGenerator());
    }

    public Object buildObject() {
        return buildObject(clazz);
    }

    private Object buildObject(Class<?> clazz) {
        //First see if the object is just one we can generate from
        //our generators.
        for(Generator generator : generators) {
            if(generator.supportsType(clazz)){
                return generator.generate(clazz, null);
            }
        }

        log.debug("[*] creating object {}.", clazz);
        Object instance = null;

        try {

            //check if we have a provider function
            if(providerFunction != null) {
                instance = providerFunction.provide();
            }

            //Check providers to see if we have something to generate this
            //bad boy for us.
            if(instance == null) {
                for (Provider provider : providers) {
                    if (provider.supportsType(clazz)) {
                        instance = provider.provide();
                    }
                }
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
            log.debug("Problems instantiating the object", e);
        } catch (IllegalAccessException e) {
            log.debug("Problems accessing properties on the object", e);
        }

        log.debug("[*] completed object: {}", instance);
        return instance;
    }

    private Object fillInstanceVariables(Object instance) {
        Field[] declaredFields = instance.getClass().getDeclaredFields();
        if(declaredFields.length == 0) return instance;
        for(Field f : declaredFields) {
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
                f.setInt(instance, RandomUtils.getRandomInt());
            } else if (type.isAssignableFrom(Double.TYPE)) {
                f.setDouble(instance, RandomUtils.getRandomDoubleObject());
            } else if (type.isAssignableFrom(Long.TYPE)) {
                f.setLong(instance, RandomUtils.getRandomLongObject());
            } else if (type.isAssignableFrom(Float.TYPE)) {
                f.setFloat(instance, RandomUtils.getRandomFloatObject());
            } else if (type.isAssignableFrom(Byte.TYPE)) {
                f.setByte(instance, RandomUtils.getRandomByte());
            } else if (type.isAssignableFrom(Short.TYPE)) {
                f.setShort(instance, RandomUtils.getRandomShortObject());
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
                Object arr = generateArray(componentType);
                f.set(instance, arr);
            } else {

                boolean generated = false;
                //Try the generators
                for(Generator generator : generators) {
                    if(generator.supportsType(type)) {
                        generated = true;
                        f.set(instance, generator.generate(type, f));
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

    Object generateArray(Class<?> type) {
        int size = RandomUtils.getRandomIntWithinRange(10);
        Object arr = Array.newInstance(type, size);

        for(int i = 0; i < size; i++) {

            //Primitives
            if (type.isAssignableFrom(Integer.TYPE)) {
                Array.setInt(arr, i, RandomUtils.getRandomInt());
            } else if (type.isAssignableFrom(Double.TYPE)) {
                Array.setDouble(arr, i, RandomUtils.getRandomDoubleObject());
            } else if (type.isAssignableFrom(Float.TYPE)) {
                Array.setFloat(arr, i, RandomUtils.getRandomFloatObject());
            } else if (type.isAssignableFrom(Long.TYPE)) {
                Array.setLong(arr, i, RandomUtils.getRandomLongObject());
            } else if (type.isAssignableFrom(Short.TYPE)) {
                Array.setShort(arr, i, RandomUtils.getRandomShortObject());
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
                        Array.set(arr, i, generator.generate(type, null));
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
