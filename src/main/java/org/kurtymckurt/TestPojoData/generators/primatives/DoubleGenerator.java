package org.kurtymckurt.TestPojoData.generators.primatives;

import org.kurtymckurt.TestPojoData.generators.Generator;
import org.kurtymckurt.TestPojoData.util.RandomUtils;

import java.lang.reflect.Field;

public class DoubleGenerator implements Generator {

    @Override
    public Object generate(Class<?> clazz, Field field) {
        return RandomUtils.getRandomDoubleObject();
    }

    @Override
    public boolean supportsType(Class<?> clazz) {
        return clazz.isAssignableFrom(Double.class);
    }
}
