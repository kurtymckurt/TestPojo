package org.kurtymckurt.TestPojoData.generators.collections;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

public class QueueGenerator extends GenericCollectionGenerator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(Queue.class) || clazz.isAssignableFrom(Deque.class) || clazz.isAssignableFrom(Iterable.class);
    }

    @Override
    Deque createInstance() {
        return new ArrayDeque();
    }
}
