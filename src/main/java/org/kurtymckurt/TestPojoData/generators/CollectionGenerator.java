package org.kurtymckurt.TestPojoData.generators;

import java.util.ArrayList;
import java.util.Collection;

public class CollectionGenerator extends GenericCollectionGenerator<Collection> {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(Collection.class);
    }

    @Override
    <T> Collection<T> createInstance() {
        return new ArrayList<>();
    }
}
