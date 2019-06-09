package org.kurtymckurt.TestPojo.generators.primatives;

import org.kurtymckurt.TestPojo.generators.Generator;
import org.kurtymckurt.TestPojo.limiters.Limiter;
import org.kurtymckurt.TestPojo.util.RandomUtils;

import java.lang.reflect.Field;
import java.util.Date;

public class DateGenerator implements Generator<Date> {

    @Override
    public Date generate(Class<?> clazz, Field field, Limiter limiter) {

        Date date = new Date(Math.abs(RandomUtils.getRandomLongObject()));
        return date;
    }

    @Override
    public boolean supportsType(Class<?> clazz) {
        return clazz.isAssignableFrom(Date.class);
    }
}
