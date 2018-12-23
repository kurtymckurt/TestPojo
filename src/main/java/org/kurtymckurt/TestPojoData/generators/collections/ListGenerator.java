package org.kurtymckurt.TestPojoData.generators.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListGenerator extends GenericCollectionGenerator {

    @Override
    boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(List.class) || clazz.isAssignableFrom(Collection.class);
    }

    @Override
    <T> Collection<T> createInstance() {
        return new ArrayList<>();
    }
}
