package org.kurtymckurt.TestPojoData.generators.primatives;

import org.kurtymckurt.TestPojoData.generators.Generator;
import org.kurtymckurt.TestPojoData.util.RandomUtils;

import java.lang.reflect.Field;

public class FloatGenerator implements Generator {

    @Override
    public Object generate(Class<?> clazz, Field field) {
        return RandomUtils.getRandomFloatObject();
    }

    @Override
    public boolean supportsType(Class<?> clazz) {
        return clazz.isAssignableFrom(Float.class);
    }
}
