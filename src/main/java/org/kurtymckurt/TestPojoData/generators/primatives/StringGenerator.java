package org.kurtymckurt.TestPojoData.generators.primatives;

import org.kurtymckurt.TestPojoData.generators.AbstractGenerator;

import java.lang.reflect.Field;

public class StringGenerator extends AbstractGenerator {

    @Override
    public Object generate(Class<?> clazz, Field field) {
        return faker.funnyName().name();
    }

    @Override
    public boolean supportsType(Class<?> clazz) {
        return clazz.isAssignableFrom(String.class);
    }
}
