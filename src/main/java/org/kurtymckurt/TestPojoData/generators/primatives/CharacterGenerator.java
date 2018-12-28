package org.kurtymckurt.TestPojoData.generators.primatives;

import org.kurtymckurt.TestPojoData.generators.Generator;
import org.kurtymckurt.TestPojoData.limiters.Limiter;
import org.kurtymckurt.TestPojoData.util.RandomUtils;

import java.lang.reflect.Field;

public class CharacterGenerator implements Generator {

    @Override
    public Object generate(Class<?> clazz, Field field, Limiter limiter) {
        return RandomUtils.getRandomCharacter();
    }

    @Override
    public boolean supportsType(Class<?> clazz) {
        return clazz.isAssignableFrom(Character.class);
    }
}
