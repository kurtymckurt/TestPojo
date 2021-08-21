package org.kurtymckurt.TestPojo.generators.primatives;

import org.kurtymckurt.TestPojo.PojoBuilderConfiguration;
import org.kurtymckurt.TestPojo.generators.Generator;
import org.kurtymckurt.TestPojo.limiters.Limiter;
import org.kurtymckurt.TestPojo.util.RandomUtils;

import java.lang.reflect.Field;

public class DoubleGenerator implements Generator<Double> {

    @Override
    public Double generate(Class<?> clazz, Field field, Limiter limiter, PojoBuilderConfiguration pojoBuilderConfiguration) {
        long min = Integer.MIN_VALUE;
        long max = Integer.MAX_VALUE;
        if(limiter != null) {
            min = limiter.getMin();
            max = limiter.getMax();
        }
        return pojoBuilderConfiguration.getRandomUtils().getRandomDoubleWithinRange(min, max);
    }

    @Override
    public boolean supportsType(Class<?> clazz) {
        return clazz.isAssignableFrom(Double.class);
    }
}
