package org.kurtymckurt.TestPojoData.generators;

import org.kurtymckurt.TestPojoData.util.RandomUtils;

public class FloatGenerator implements Generator {

    @Override
    public Object generate() {
        return RandomUtils.getRandomFloatObject();
    }

    @Override
    public boolean supportsType(Class<?> clazz) {
        return clazz.isAssignableFrom(Float.class);
    }
}
