package org.kurtymckurt.TestPojoData.generators;

import org.kurtymckurt.TestPojoData.PojoBuilder;
import org.kurtymckurt.TestPojoData.PojoBuilderConfiguration;
import org.kurtymckurt.TestPojoData.util.RandomUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

public abstract class GenericCollectionGenerator implements Generator {
    @Override
    public Object generate(Class<?> clazz, Field field) {
        Collection<Object> instance = createInstance();
        Class<?> newClazz = (Class<?>)((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
        PojoBuilder pojoBuilder = new PojoBuilder(
                PojoBuilderConfiguration.builder()
                        .clazz(newClazz)
                        .build());
        int size = RandomUtils.getRandomIntWithinRange(100);
        for(int i = 0; i < size; i++){
            instance.add(pojoBuilder.buildObject());
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
