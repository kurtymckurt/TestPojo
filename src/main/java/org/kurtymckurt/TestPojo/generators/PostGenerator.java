package org.kurtymckurt.TestPojo.generators;

@FunctionalInterface
public interface PostGenerator<K, T> {
    T generate(K k);
}
