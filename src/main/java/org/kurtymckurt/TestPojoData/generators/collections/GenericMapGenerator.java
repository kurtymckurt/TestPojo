package org.kurtymckurt.TestPojoData.generators.collections;

import org.kurtymckurt.TestPojoData.PojoBuilder;
import org.kurtymckurt.TestPojoData.PojoBuilderConfiguration;
import org.kurtymckurt.TestPojoData.generators.Generator;
import org.kurtymckurt.TestPojoData.util.RandomUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

public abstract class GenericMapGenerator implements Generator {
    @Override
    public Object generate(Class<?> clazz, Field field) {

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
        int size = RandomUtils.getRandomIntWithinRange(1,100);
        for(int i = 0; i < size; i++){
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
