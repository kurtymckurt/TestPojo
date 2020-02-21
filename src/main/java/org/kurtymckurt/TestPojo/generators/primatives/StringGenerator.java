package org.kurtymckurt.TestPojo.generators.primatives;

import com.mifmif.common.regex.Generex;
import org.kurtymckurt.TestPojo.PojoBuilderConfiguration;
import org.kurtymckurt.TestPojo.generators.Generator;
import org.kurtymckurt.TestPojo.limiters.Limiter;
import org.kurtymckurt.TestPojo.util.RandomUtils;

import java.lang.reflect.Field;

public class StringGenerator implements Generator<String> {

    private static final String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456780";

    @Override
    public String generate(Class<?> clazz, Field field, Limiter limiter, PojoBuilderConfiguration pojoBuilderConfiguration) {

        if (limiter != null && limiter.getRegex() != null) {
            Generex generex = new Generex(limiter.getRegex());
            return generex.random();
        } else {
            return getStringBasedOnSizeLimits(limiter);
        }
    }

    private String getStringBasedOnSizeLimits(Limiter limiter) {
        long min = 1;
        long max = 100;
        long length = RandomUtils.getRandomIntWithinRange(min, max);
        boolean hasLimiter = limiter != null;
        if (hasLimiter) {
            if (limiter.getMin() != null) {
                min = limiter.getMin();
            }
            if (limiter.getMax() != null) {
                max = limiter.getMax();
            }
            if (limiter.getLength() != null) {
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
