package org.kurtymckurt.TestPojoData.generators.primatives;

import org.kurtymckurt.TestPojoData.generators.Generator;
import org.kurtymckurt.TestPojoData.limiters.Limiter;
import org.kurtymckurt.TestPojoData.util.RandomUtils;

import java.lang.reflect.Field;

public class IntegerGenerator implements Generator {

    @Override
    public Object generate(Class<?> clazz, Field field, Limiter limiter) {
        long min = Integer.MIN_VALUE;
        long max = Integer.MAX_VALUE;
        if(limiter != null) {
            if(limiter.getMin() != null) {
                min = limiter.getMin();
            }
            if(limiter.getMax() != null) {
                max = limiter.getMax();
            }
        }
        return RandomUtils.getRandomIntWithinRange(min, max);
    }

    @Override
    public boolean supportsType(Class<?> clazz) {
        return clazz.isAssignableFrom(Integer.class);
    }
}
