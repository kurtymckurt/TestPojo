package org.kurtymckurt.TestPojoData.generators.collections;

import java.util.HashSet;
import java.util.Set;

public class SetGenerator extends GenericCollectionGenerator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(Set.class);
    }

    @Override
    Set createInstance() {
        return new HashSet<>();
    }
}
