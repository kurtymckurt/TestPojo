package org.kurtymckurt.TestPojoData.generators;

import org.kurtymckurt.TestPojoData.util.RandomUtils;

public class LongGenerator implements Generator {

    @Override
    public Object generate() {
        return RandomUtils.getRandomLongObject();
    }

    @Override
    public boolean supportsType(Class<?> clazz) {
        return clazz.isAssignableFrom(Long.class);
    }
}
