package org.kurtymckurt.TestPojoData.generators;

import org.kurtymckurt.TestPojoData.util.RandomUtils;

public class CharacterGenerator implements Generator {

    @Override
    public Object generate() {
        return RandomUtils.getRandomCharacter();
    }

    @Override
    public boolean supportsType(Class<?> clazz) {
        return clazz.isAssignableFrom(Character.class);
    }
}
