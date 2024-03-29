package org.kurtymckurt.TestPojo.generators.primatives;

import org.kurtymckurt.TestPojo.PojoBuilderConfiguration;
import org.kurtymckurt.TestPojo.generators.Generator;
import org.kurtymckurt.TestPojo.limiters.Limiter;

import java.lang.reflect.Field;

public class BooleanGenerator implements Generator<Boolean> {

    @Override
    public Boolean generate(Class<?> clazz, Field field, Limiter limiter, PojoBuilderConfiguration pojoBuilderConfiguration) {
        return pojoBuilderConfiguration.getRandomUtils().getRandomBoolean();
    }

    @Override
    public boolean supportsType(Class<?> clazz) {
        return clazz.isAssignableFrom(Boolean.class);
    }
}
