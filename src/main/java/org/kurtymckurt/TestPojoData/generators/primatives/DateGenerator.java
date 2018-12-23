package org.kurtymckurt.TestPojoData.generators.primatives;

import org.kurtymckurt.TestPojoData.generators.Generator;
import org.kurtymckurt.TestPojoData.util.RandomUtils;

import java.lang.reflect.Field;
import java.util.Date;

public class DateGenerator implements Generator {

    @Override
    public Object generate(Class<?> clazz, Field field) {

        Date date = new Date(Math.abs(RandomUtils.getRandomLongObject()));
        return date;
    }

    @Override
    public boolean supportsType(Class<?> clazz) {
        return clazz.isAssignableFrom(Date.class);
    }
}
