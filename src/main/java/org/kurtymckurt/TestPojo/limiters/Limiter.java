package org.kurtymckurt.TestPojo.limiters;

public interface Limiter {
   default boolean isStringLimiter() {
      return getClass().isAssignableFrom(StringLimiter.class);
   }
   default boolean isCollectionLimiter() {
      return getClass().isAssignableFrom(CollectionLimiter.class);
   }
   default boolean isArrayLimiter() {
      return getClass().isAssignableFrom(ArrayLimiter.class);
   }
   default boolean isNumberLimiter() {
      return getClass().isAssignableFrom(NumberLimiter.class);
   }
}
