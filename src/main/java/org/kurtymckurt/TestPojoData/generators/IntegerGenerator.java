package org.kurtymckurt.TestPojoData.generators;

import org.kurtymckurt.TestPojoData.util.RandomUtils;

public class IntegerGenerator implements Generator {

    @Override
    public Object generate() {
        return RandomUtils.getRandomInt();
    }

    @Override
    public boolean supportsType(Class<?> clazz) {
        return clazz.isAssignableFrom(Integer.class);
    }
}
