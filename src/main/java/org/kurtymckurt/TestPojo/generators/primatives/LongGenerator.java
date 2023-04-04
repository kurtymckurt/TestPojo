package org.kurtymckurt.TestPojo.generators.primatives;

import org.kurtymckurt.TestPojo.PojoBuilderConfiguration;
import org.kurtymckurt.TestPojo.generators.Generator;
import org.kurtymckurt.TestPojo.limiters.Limiter;
import org.kurtymckurt.TestPojo.limiters.NumberLimiter;
import org.kurtymckurt.TestPojo.util.LimiterUtils;
import org.kurtymckurt.TestPojo.util.NumberNullSafeLimits;

import java.lang.reflect.Field;

public class LongGenerator implements Generator<Long> {

    @Override
    public Long generate(Class<?> clazz, Field field, Limiter limiter, PojoBuilderConfiguration pojoBuilderConfiguration) {
        final NumberLimiter nlimiter = LimiterUtils.getNumberLimiter(limiter);
        NumberNullSafeLimits nullSafeLimits = LimiterUtils.getNumberNullSafeLimits(
                0, 100, nlimiter, pojoBuilderConfiguration.getRandomUtils());
        return pojoBuilderConfiguration.getRandomUtils().getRandomLongWithinRange(nullSafeLimits.min, nullSafeLimits.max);
    }

    @Override
    public boolean supportsType(Class<?> clazz) {
        return clazz.isAssignableFrom(Long.class);
    }
}
