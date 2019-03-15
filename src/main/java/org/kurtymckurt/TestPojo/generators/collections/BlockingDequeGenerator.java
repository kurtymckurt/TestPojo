package org.kurtymckurt.TestPojo.generators.collections;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class BlockingDequeGenerator extends GenericCollectionGenerator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(BlockingDeque.class);
    }

    @Override
    BlockingDeque createInstance() {
        return new LinkedBlockingDeque();
    }
}
