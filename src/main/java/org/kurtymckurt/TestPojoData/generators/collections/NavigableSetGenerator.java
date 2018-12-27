package org.kurtymckurt.TestPojoData.generators.collections;

import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

public class NavigableSetGenerator extends GenericCollectionGenerator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(NavigableSet.class);
    }

    @Override
    Set createInstance() {
        return new TreeSet<>();
    }
}
