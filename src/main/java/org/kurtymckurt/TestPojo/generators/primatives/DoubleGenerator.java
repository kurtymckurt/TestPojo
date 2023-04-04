package org.kurtymckurt.TestPojo.generators.primatives;

import org.kurtymckurt.TestPojo.PojoBuilderConfiguration;
import org.kurtymckurt.TestPojo.generators.Generator;
import org.kurtymckurt.TestPojo.limiters.Limiter;
import org.kurtymckurt.TestPojo.limiters.NumberLimiter;
import org.kurtymckurt.TestPojo.util.LimiterUtils;

import java.lang.reflect.Field;

public class DoubleGenerator implements Generator<Double> {

    @Override
    public Double generate(Class<?> clazz, Field field, Limiter limiter, PojoBuilderConfiguration pojoBuilderConfiguration) {
        final NumberLimiter nlimiter = LimiterUtils.getNumberLimiter(limiter);
        long min = Integer.MIN_VALUE;
        long max = Integer.MAX_VALUE;
        if(nlimiter != null) {
            min = nlimiter.getMin();
            max = nlimiter.getMax();
        }
        return pojoBuilderConfiguration.getRandomUtils().getRandomDoubleWithinRange(min, max);
    }

    @Override
    public boolean supportsType(Class<?> clazz) {
        return clazz.isAssignableFrom(Double.class);
    }
}
