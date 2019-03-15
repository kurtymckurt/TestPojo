package org.kurtymckurt.TestPojo.generators.primatives;

import org.kurtymckurt.TestPojo.generators.Generator;
import org.kurtymckurt.TestPojo.limiters.Limiter;
import org.kurtymckurt.TestPojo.util.RandomUtils;

import java.lang.reflect.Field;

public class ShortGenerator implements Generator {

    @Override
    public Object generate(Class<?> clazz, Field field, Limiter limiter) {
        long min = Integer.MIN_VALUE;
        long max = Integer.MAX_VALUE;
        if(limiter != null) {
            min = limiter.getMin();
            max = limiter.getMax();
        }
        return RandomUtils.getRandomShortWithinRange(min, max);
    }

    @Override
    public boolean supportsType(Class<?> clazz) {
        return clazz.isAssignableFrom(Short.class);
    }
}
