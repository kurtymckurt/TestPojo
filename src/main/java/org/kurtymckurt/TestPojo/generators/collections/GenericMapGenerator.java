package org.kurtymckurt.TestPojo.generators.collections;

import org.kurtymckurt.TestPojo.PojoBuilder;
import org.kurtymckurt.TestPojo.PojoBuilderConfiguration;
import org.kurtymckurt.TestPojo.generators.Generator;
import org.kurtymckurt.TestPojo.limiters.Limiter;
import org.kurtymckurt.TestPojo.util.LimiterUtils;
import org.kurtymckurt.TestPojo.util.NullSafeLimits;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

public abstract class GenericMapGenerator implements Generator {
    @Override
    public Object generate(Class<?> clazz, Field field, Limiter limiter) {

        Map<Object,Object> map = createInstance();
        Type[] actualTypeArguments = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
        Class<?> key = (Class<?>)actualTypeArguments[0];
        Class<?> value = (Class<?>) actualTypeArguments[1];

        PojoBuilder keyBuilder = new PojoBuilder(
              PojoBuilderConfiguration.builder()
                    .clazz(key)
                    .build());
        PojoBuilder valueBuilder = new PojoBuilder(
              PojoBuilderConfiguration.builder()
                    .clazz(value)
                    .build());

        NullSafeLimits nullSafeLimits = LimiterUtils.getNullSafeLimits(0, 100, limiter);
        for(int i = 0; i < nullSafeLimits.size; i++){
            map.put(keyBuilder.buildObject(), valueBuilder.buildObject());
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
