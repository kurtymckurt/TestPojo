package org.kurtymckurt.TestPojo.generators.collections;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

public class QueueGenerator<T> extends GenericCollectionGenerator<T> {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(Queue.class) || clazz.isAssignableFrom(Deque.class) || clazz.isAssignableFrom(Iterable.class);
    }

    @Override
    Deque<T> createInstance() {
        return new ArrayDeque();
    }
}
