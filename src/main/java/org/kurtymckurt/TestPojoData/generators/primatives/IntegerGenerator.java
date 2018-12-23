package org.kurtymckurt.TestPojoData.generators.primatives;

import org.kurtymckurt.TestPojoData.generators.Generator;
import org.kurtymckurt.TestPojoData.util.RandomUtils;

import java.lang.reflect.Field;

public class IntegerGenerator implements Generator {

    @Override
    public Object generate(Class<?> clazz, Field field) {
        return RandomUtils.getRandomInt();
    }

    @Override
    public boolean supportsType(Class<?> clazz) {
        return clazz.isAssignableFrom(Integer.class);
    }
}
