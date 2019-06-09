package org.kurtymckurt.TestPojo.generators.collections;

import java.util.HashSet;
import java.util.Set;

public class SetGenerator<T> extends GenericCollectionGenerator<T> {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(Set.class);
    }

    @Override
    Set<T> createInstance() {
        return new HashSet<>();
    }
}
