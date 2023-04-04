package org.kurtymckurt.TestPojo.generators.primatives;

import org.kurtymckurt.TestPojo.PojoBuilderConfiguration;
import org.kurtymckurt.TestPojo.generators.Generator;
import org.kurtymckurt.TestPojo.limiters.Limiter;

import java.lang.reflect.Field;
import java.util.Date;

public class DateGenerator implements Generator<Date> {

    @Override
    public Date generate(Class<?> clazz, Field field, Limiter limiter, PojoBuilderConfiguration pojoBuilderConfiguration) {
        return new Date(Math.abs(pojoBuilderConfiguration.getRandomUtils().getRandomLongObject()));
    }

    @Override
    public boolean supportsType(Class<?> clazz) {
        return clazz.isAssignableFrom(Date.class);
    }
}
