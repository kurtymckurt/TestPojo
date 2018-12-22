package org.kurtymckurt.TestPojoData.generators;

import org.kurtymckurt.TestPojoData.util.RandomUtils;

public class ByteGenerator implements Generator{

    @Override
    public Object generate() {
        return RandomUtils.getRandomByte();
    }

    @Override
    public boolean supportsType(Class<?> clazz) {
        return clazz.isAssignableFrom(Byte.class);
    }
}
