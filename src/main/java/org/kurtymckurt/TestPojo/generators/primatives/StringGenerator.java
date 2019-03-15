package org.kurtymckurt.TestPojo.generators.primatives;

import org.kurtymckurt.TestPojo.generators.Generator;
import org.kurtymckurt.TestPojo.limiters.Limiter;
import org.kurtymckurt.TestPojo.util.RandomUtils;

import java.lang.reflect.Field;

public class StringGenerator implements Generator {

    private static final String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456780";

    @Override
    public Object generate(Class<?> clazz, Field field, Limiter limiter) {
        long min = 1;
        long max = 100;
        long length = RandomUtils.getRandomIntWithinRange(min, max);
        if(limiter != null) {
            if(limiter.getMin() != null) {
                min = limiter.getMin();
            }
            if(limiter.getMax() != null) {
                max = limiter.getMax();
            }
            if(limiter.getLength() != null) {
                length = limiter.getLength();
            } else {
                length = RandomUtils.getRandomIntWithinRange(min, max);
            }
        }

        return getChars(length);
    }

    private String getChars(long length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++){
            builder.append(characters.charAt(RandomUtils.getRandomIntWithinRange(0, characters.length()-1)));
        }
        return builder.toString();
    }

    @Override
    public boolean supportsType(Class<?> clazz) {
        return clazz.isAssignableFrom(String.class);
    }
}
