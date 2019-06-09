package org.kurtymckurt.TestPojo.generators.collections;

import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

public class NavigableSetGenerator<T> extends GenericCollectionGenerator<T> {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(NavigableSet.class);
    }

    @Override
    Set<T> createInstance() {
        return new TreeSet<>();
    }
}
