package org.kurtymckurt.TestPojo.generators.collections;

import org.kurtymckurt.TestPojo.PojoBuilder;
import org.kurtymckurt.TestPojo.PojoBuilderConfiguration;
import org.kurtymckurt.TestPojo.generators.Generator;
import org.kurtymckurt.TestPojo.limiters.CollectionLimiter;
import org.kurtymckurt.TestPojo.limiters.Limiter;
import org.kurtymckurt.TestPojo.util.CollectionNullSafeLimits;
import org.kurtymckurt.TestPojo.util.LimiterUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

public abstract class GenericCollectionGenerator<T> implements Generator<Collection<T>> {
    @Override
    public Collection<T> generate(Class<?> clazz, Field field, Limiter limiter, PojoBuilderConfiguration pojoBuilderConfiguration) {
        final CollectionLimiter cLimiter = LimiterUtils.getCollectionLimiter(limiter);
        final Collection<T> instance = createInstance();
        final Class<?> newClazz = (Class<?>)((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
        final CollectionNullSafeLimits nullSafeLimits = LimiterUtils.getCollectionNullSafeLimits(
                1, 100, cLimiter, pojoBuilderConfiguration.getRandomUtils());

        final PojoBuilder newPojoBuilder = new PojoBuilder(pojoBuilderConfiguration.toBuilder().clazz(newClazz).build());
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
