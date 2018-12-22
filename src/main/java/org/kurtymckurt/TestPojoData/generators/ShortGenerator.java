package org.kurtymckurt.TestPojoData.generators;

import org.kurtymckurt.TestPojoData.util.RandomUtils;

import java.lang.reflect.Field;

public class ShortGenerator implements Generator {

    @Override
    public Object generate(Class<?> clazz, Field field) {
        return RandomUtils.getRandomShortObject();
    }

    @Override
    public boolean supportsType(Class<?> clazz) {
        return clazz.isAssignableFrom(Short.class);
    }
}
