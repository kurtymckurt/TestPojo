package org.kurtymckurt.TestPojo.generators.primatives;

import org.kurtymckurt.TestPojo.PojoBuilderConfiguration;
import org.kurtymckurt.TestPojo.generators.Generator;
import org.kurtymckurt.TestPojo.limiters.Limiter;
import org.kurtymckurt.TestPojo.util.LimiterUtils;
import org.kurtymckurt.TestPojo.util.NullSafeLimits;
import org.kurtymckurt.TestPojo.util.RandomUtils;

import java.lang.reflect.Field;

public class LongGenerator implements Generator<Long> {

    @Override
    public Long generate(Class<?> clazz, Field field, Limiter limiter, PojoBuilderConfiguration pojoBuilderConfiguration) {
        NullSafeLimits nullSafeLimits = LimiterUtils.getNullSafeLimits(
                0, 100, limiter, pojoBuilderConfiguration.getRandomUtils());
        return pojoBuilderConfiguration.getRandomUtils().getRandomLongWithinRange(nullSafeLimits.min, nullSafeLimits.max);
    }

    @Override
    public boolean supportsType(Class<?> clazz) {
        return clazz.isAssignableFrom(Long.class);
    }
}
