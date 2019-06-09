package org.kurtymckurt.TestPojo.generators.collections;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class BlockingDequeGenerator<T> extends GenericCollectionGenerator<T> {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(BlockingDeque.class);
    }

    @Override
    BlockingDeque<T> createInstance() {
        return new LinkedBlockingDeque();
    }
}
