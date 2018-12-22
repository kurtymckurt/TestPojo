package org.kurtymckurt.TestPojoData.generators;

import org.kurtymckurt.TestPojoData.util.RandomUtils;

import java.lang.reflect.Field;

public class ByteGenerator implements Generator{

    @Override
    public Object generate(Class<?> clazz, Field field) {
        return RandomUtils.getRandomByte();
    }

    @Override
    public boolean supportsType(Class<?> clazz) {
        return clazz.isAssignableFrom(Byte.class);
    }
}
