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
import java.lang.reflect.Type;
import java.util.Map;

public abstract class GenericMapGenerator<K,V> implements Generator<Map<K,V>> {
    @Override
    public Map<K,V> generate(Class<?> clazz, Field field, Limiter limiter, PojoBuilderConfiguration pojoBuilderConfiguration) {
        final CollectionLimiter cLimiter = LimiterUtils.getCollectionLimiter(limiter);
        final Map<K,V> map = createInstance();
        final Type[] actualTypeArguments = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
        final Class<?> key = (Class<?>)actualTypeArguments[0];
        final Class<?> value = (Class<?>) actualTypeArguments[1];

        final PojoBuilder keyBuilder = new PojoBuilder(
                pojoBuilderConfiguration
                     .toBuilder()
                     .clazz(key)
                     .build());
        final PojoBuilder valueBuilder = new PojoBuilder(
                pojoBuilderConfiguration
                        .toBuilder()
                        .clazz(value)
                        .build());

        final CollectionNullSafeLimits nullSafeLimits = LimiterUtils.getCollectionNullSafeLimits(
                0, 100, cLimiter, pojoBuilderConfiguration.getRandomUtils());
        for(int i = 0; i < nullSafeLimits.size; i++){
            map.put( (K) keyBuilder.buildObject(), (V) valueBuilder.buildObject());
        }

        return map;
    }

    @Override
    public boolean supportsType(Class<?> clazz) {
        return supports(clazz);
    }

    abstract boolean supports(Class<?> clazz);
    abstract <K,V> Map<K,V> createInstance();
}
