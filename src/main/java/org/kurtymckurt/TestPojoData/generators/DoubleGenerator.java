package org.kurtymckurt.TestPojoData.generators;

import org.kurtymckurt.TestPojoData.util.RandomUtils;

public class DoubleGenerator implements Generator {

    @Override
    public Object generate() {
        return RandomUtils.getRandomDoubleObject();
    }

    @Override
    public boolean supportsType(Class<?> clazz) {
        return clazz.isAssignableFrom(Double.class);
    }
}
