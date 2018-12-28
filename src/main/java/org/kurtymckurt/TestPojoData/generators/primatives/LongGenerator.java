package org.kurtymckurt.TestPojoData.generators.primatives;

import org.kurtymckurt.TestPojoData.generators.Generator;
import org.kurtymckurt.TestPojoData.limiters.Limiter;
import org.kurtymckurt.TestPojoData.util.LimiterUtils;
import org.kurtymckurt.TestPojoData.util.NullSafeLimits;
import org.kurtymckurt.TestPojoData.util.RandomUtils;

import java.lang.reflect.Field;

public class LongGenerator implements Generator {

    @Override
    public Object generate(Class<?> clazz, Field field, Limiter limiter) {
        NullSafeLimits nullSafeLimits = LimiterUtils.getNullSafeLimits(0, 100, 100, limiter);
        return RandomUtils.getRandomLongWithinRange(nullSafeLimits.min, nullSafeLimits.max);
    }

    @Override
    public boolean supportsType(Class<?> clazz) {
        return clazz.isAssignableFrom(Long.class);
    }
}
