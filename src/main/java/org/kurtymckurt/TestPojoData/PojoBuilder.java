package org.kurtymckurt.TestPojoData;


import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.security.SecureRandom;
import java.util.UUID;

/****
 * Actually does the logic of creating the pojo using reflection.
 */
@Slf4j
public class PojoBuilder {

    private static SecureRandom random = new SecureRandom();

    public static <T> T buildObject(PojoBuilderDescriptor<T> builder) {
        Class<T> clazz = builder.getClazz();
        log.debug("[*] creating object {}.", clazz);
        T instance = null;
        try {
            instance = clazz.newInstance();
            log.debug("[*] created object {}.", instance);
            log.debug("[*] attempting to fill the object {}.", instance);
            instance = fillInstanceVariables(instance);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        log.debug("[*] completed object: {}", instance);
        return instance;
    }

    public static Object buildObject(Class<?> clazz) {
        log.debug("[*] creating object {}.", clazz);
        Object instance = null;
        try {
            instance = clazz.newInstance();
            log.debug("[*] created object {}.", instance);
            log.debug("[*] attempting to fill the object {}.", instance);
            instance = fillInstanceVariables(instance);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        log.debug("[*] completed object: {}", instance);
        return instance;
    }

    private static <T> T fillInstanceVariables(T instance) {
        Field[] declaredFields = instance.getClass().getDeclaredFields();
        if(declaredFields.length == 0) return instance;
        for(Field f : declaredFields) {
            f.setAccessible(true);
            setField(instance, f);
        }
        return instance;
    }

    static <T> void setField(T instance, Field f) {
        Class<?> type = f.getType();
        log.debug("[*] attempting to generate field name: {}, field: {}.", f.getName(), type);
        try {
            if (type.isAssignableFrom(Integer.class)) {
                f.set(instance, getRandomInt());
            } else if (type.isAssignableFrom(Integer.TYPE)) {
                f.setInt(instance, getRandomInt());
            } else if (type.isAssignableFrom(Double.class)) {
                f.set(instance, getRandomDoubleObject());
            } else if (type.isAssignableFrom(Double.TYPE)) {
                f.setDouble(instance, getRandomDoubleObject());
            } else if (type.isAssignableFrom(Long.class)) {
                f.set(instance, getRandomLongObject());
            } else if (type.isAssignableFrom(Long.TYPE)) {
                f.setLong(instance, getRandomLongObject());
            } else if (type.isAssignableFrom(Float.class)) {
                f.set(instance, getRandomFloatObject());
            } else if (type.isAssignableFrom(Float.TYPE)) {
                f.setFloat(instance, getRandomFloatObject());
            } else if (type.isAssignableFrom(Byte.class)) {
                f.set(instance, getRandomByte()[0]);
            } else if (type.isAssignableFrom(Byte.TYPE)) {
                f.setByte(instance, getRandomByte()[0]);
            } else if (type.isAssignableFrom(Short.class)) {
                f.set(instance, getRandomShortObject());
            } else if (type.isAssignableFrom(Short.TYPE)) {
                f.setShort(instance, getRandomShortObject());
            } else if (type.isAssignableFrom(Boolean.class)) {
                f.set(instance, getRandomBoolean());
            } else if (type.isAssignableFrom(Boolean.TYPE)) {
                f.setBoolean(instance, getRandomBoolean());
            } else if (type.isAssignableFrom(Character.class)) {
                f.set(instance, getRandomCharacter());
            } else if (type.isAssignableFrom(Character.TYPE)) {
                f.setChar(instance, getRandomCharacter());
            } else if (type.isAssignableFrom(String.class)) {
                f.set(instance, getRandomString());
            } else if (type.isEnum()) {
                f.set(instance,
                        type.getEnumConstants()[
                                getRandomIntWithinRange(type.getEnumConstants().length)]);
            } else if(type.isArray()) {
                Class<?> componentType = type.getComponentType();
                log.debug("[*] creating array of type: {}", componentType);
                Object arr = generateArray(componentType);
                f.set(instance, arr);
            } else {
                //try a custom object
                f.set(instance, buildObject(type));
            }
        } catch (IllegalAccessException e) {
            log.debug("[*] Exception trying to generate the field {}", type.getTypeName(), e);
        }

    }

    static Object generateArray(Class<?> type) {
        int size = getRandomIntWithinRange(10);
        Object arr = Array.newInstance(type, size);

        for(int i = 0; i < size; i++) {
            if (type.isAssignableFrom(Integer.TYPE)) {
                Array.setInt(arr, i, getRandomInt());
            } else if (type.isAssignableFrom(Double.TYPE)) {
                Array.setDouble(arr, i, getRandomDoubleObject());
            } else if (type.isAssignableFrom(Float.TYPE)) {
                Array.setFloat(arr, i, getRandomFloatObject());
            } else if (type.isAssignableFrom(Long.TYPE)) {
                Array.setLong(arr, i, getRandomLongObject());
            } else if (type.isAssignableFrom(Short.TYPE)) {
                Array.setShort(arr, i, getRandomShortObject());
            } else if (type.isAssignableFrom(Byte.TYPE)) {
                Array.setByte(arr, i, getRandomByte()[0]);
            } else if (type.isAssignableFrom(Character.TYPE)) {
                Array.setChar(arr, i, getRandomCharacter());
            } else if (type.isAssignableFrom(Boolean.TYPE)) {
                Array.setBoolean(arr, i, getRandomBoolean());
            } else if(type.isAssignableFrom(String.class)) {
                Array.set(arr, i, getRandomString());
            } else if (type.isAssignableFrom(Integer.class)) {
                Array.set(arr, i , getRandomInt());
            } else if (type.isAssignableFrom(Double.class)) {
                Array.set(arr, i , getRandomDoubleObject());
            } else if (type.isAssignableFrom(Long.class)) {
                Array.set(arr, i , getRandomLongObject());
            } else if (type.isAssignableFrom(Character.class)) {
                Array.set(arr, i , getRandomCharacter());
            } else if (type.isAssignableFrom(Byte.class)) {
                Array.set(arr, i , getRandomByte());
            } else if (type.isAssignableFrom(Float.class)) {
                Array.set(arr, i , getRandomFloatObject());
            } else if (type.isAssignableFrom(Boolean.class)) {
                Array.set(arr, i , getRandomBoolean());
            } else if (type.isAssignableFrom(Short.class)) {
                Array.set(arr, i , getRandomShortObject());
            } else {
                //Try a custom object
                Array.set(arr, i, buildObject(type));
            }
        }

        return arr;
    }

    /***
     * Get random integer within a range exclusively.
     * @param max
     * @return
     */
    static int getRandomIntWithinRange(int max) {
        return random.nextInt(max);
    }

    static int getRandomInt() {
        return getRandomIntWithinRange(Integer.MAX_VALUE);
    }

    static String getRandomString() {
        return UUID.randomUUID().toString();
    }

    static Double getRandomDoubleObject() {
        return Double.valueOf(random.nextDouble());
    }

    static Float getRandomFloatObject() {
        return Float.valueOf(random.nextFloat());
    }

    static Long getRandomLongObject() {
        return Long.valueOf(random.nextLong());
    }

    static Short getRandomShortObject() {
        return Short.valueOf((short)random.nextInt(Short.MAX_VALUE));
    }

    static byte[] getRandomByte() {
        byte[] bytes = new byte[1];
        random.nextBytes(bytes);
        return bytes;
    }

    static Boolean getRandomBoolean() {
        return random.nextBoolean();
    }

    static Character getRandomCharacter() {
        String uuid = UUID.randomUUID().toString();
        return uuid.charAt(getRandomIntWithinRange(uuid.length()));
    }



}
