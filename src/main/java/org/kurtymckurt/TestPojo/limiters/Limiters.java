package org.kurtymckurt.TestPojo.limiters;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Limiters {

    public StringLimiter.StringLimiterBuilder stringLimiter() {
        return StringLimiter.builder();
    }

    public CollectionLimiter.CollectionLimiterBuilder collectionLimiter() {
        return CollectionLimiter.builder();
    }

    public NumberLimiter.NumberLimiterBuilder numberLimiter() {
        return NumberLimiter.builder();
    }

    public ArrayLimiter.ArrayLimiterBuilder arrayLimiter() {
        return ArrayLimiter.builder();
    }
}
