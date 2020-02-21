package org.kurtymckurt.TestPojo.generators.collections;

import org.kurtymckurt.TestPojo.PojoBuilder;
import org.kurtymckurt.TestPojo.PojoBuilderConfiguration;
import org.kurtymckurt.TestPojo.generators.Generator;
import org.kurtymckurt.TestPojo.limiters.Limiter;
import org.kurtymckurt.TestPojo.util.LimiterUtils;
import org.kurtymckurt.TestPojo.util.NullSafeLimits;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

public abstract class GenericCollectionGenerator<T> implements Generator<Collection<T>> {
    @Override
    public Collection<T> generate(Class<?> clazz, Field field, Limiter limiter, PojoBuilderConfiguration pojoBuilderConfiguration) {
        Collection<T> instance = createInstance();
        Class<?> newClazz = (Class<?>)((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
        NullSafeLimits nullSafeLimits = LimiterUtils.getNullSafeLimits(1, 100, limiter);

        PojoBuilder newPojoBuilder = new PojoBuilder(pojoBuilderConfiguration.toBuilder().clazz(newClazz).build());
        for(int i = 0; i < nullSafeLimits.size; i++){
            instance.add((T) newPojoBuilder.buildObject());
        }

        return instance;
    }

    @Override
    public boolean supportsType(Class<?> clazz) {
        return supports(clazz);
    }

    abstract boolean supports(Class<?> clazz);
    abstract <T> Collection<T> createInstance();
}
