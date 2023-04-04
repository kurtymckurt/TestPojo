package org.kurtymckurt.TestPojo.generators.primatives;

import com.mifmif.common.regex.Generex;
import org.kurtymckurt.TestPojo.PojoBuilderConfiguration;
import org.kurtymckurt.TestPojo.generators.Generator;
import org.kurtymckurt.TestPojo.limiters.Limiter;
import org.kurtymckurt.TestPojo.limiters.StringLimiter;
import org.kurtymckurt.TestPojo.util.LimiterUtils;
import org.kurtymckurt.TestPojo.util.RandomUtils;

import java.lang.reflect.Field;
import java.util.List;

public class StringGenerator implements Generator<String> {

    private static final String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456780";

    @Override
    public String generate(Class<?> clazz, Field field, Limiter limiter, PojoBuilderConfiguration pojoBuilderConfiguration) {
        final StringLimiter sLimiter = LimiterUtils.getStringLimiter(limiter);
        if (sLimiter != null && sLimiter.getRegex() != null) {
            Generex generex = new Generex(sLimiter.getRegex());
            return generex.random();
        } else {
            return getStringBasedOnLimits(sLimiter, pojoBuilderConfiguration.getRandomUtils());
        }
    }

    private String getStringBasedOnLimits(StringLimiter limiter, RandomUtils randomUtils) {
        long min = 1;
        long max = 100;
        long length = randomUtils.getRandomIntWithinRange(min, max);
        boolean hasLimiter = limiter != null;
        if (hasLimiter) {
            // If they set that they want a value from a provided list of values...
            List<String> potentialValues = limiter.getPotentialValues();
            if (potentialValues != null && potentialValues.size() > 0) {
                return potentialValues.get(randomUtils.getRandomIntWithinRange(potentialValues.size()));
            }
            if (limiter.getMin() != null) {
                min = limiter.getMin();
            }
            if (limiter.getMax() != null) {
                max = limiter.getMax();
            }
            if (limiter.getLength() != null) {
                length = limiter.getLength();
            } else {
                length = randomUtils.getRandomIntWithinRange(min, max);
            }
        }

        return getChars(length, randomUtils);
    }


    private String getChars(long length, RandomUtils randomUtils) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++){
            builder.append(characters.charAt(randomUtils.getRandomIntWithinRange(0, characters.length()-1)));
        }
        return builder.toString();
    }

    @Override
    public boolean supportsType(Class<?> clazz) {
        return clazz.isAssignableFrom(String.class);
    }
}
