package org.kurtymckurt.TestPojo.providers;

@FunctionalInterface
public interface ProviderFunction<T> {
   T provide();
}
