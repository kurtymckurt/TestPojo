package org.kurtymckurt.TestPojo.util;

import lombok.experimental.UtilityClass;
import org.kurtymckurt.TestPojo.limiters.*;

@UtilityClass
public class LimiterUtils {

    public CollectionLimiter getCollectionLimiter(Limiter limiter) {
        if(limiter == null)  {
            return null;
        }
        return limiter.isCollectionLimiter() ? (CollectionLimiter) limiter : null;
    }

    public NumberLimiter getNumberLimiter(Limiter limiter) {
        if(limiter == null)  {
            return null;
        }
        return limiter.isNumberLimiter() ? (NumberLimiter) limiter : null;
    }

    public ArrayLimiter getArrayLimiter(Limiter limiter) {
        if(limiter == null)  {
            return null;
        }
        return limiter.isArrayLimiter() ? (ArrayLimiter) limiter : null;
    }

    public StringLimiter getStringLimiter(Limiter limiter) {
        if(limiter == null)  {
            return null;
        }
        return limiter.isStringLimiter() ? (StringLimiter) limiter : null;
    }

    public NumberLimiter fromArrayLimiter(ArrayLimiter limiter) {
        if(limiter == null) {
            return null;
        }
        return NumberLimiter.builder().max(limiter.getMax()).min(limiter.getMin()).build();
    }

    public NumberNullSafeLimits getNumberNullSafeLimits(int minDefault, int maxDefault, NumberLimiter limiter, RandomUtils randomUtils) {
        long min = minDefault;
        long max = maxDefault;
        if(limiter != null) {
            if(limiter.getMin() != null) {
                min = limiter.getMin();
            }
            if(limiter.getMax() != null) {
                max = limiter.getMax();
            }
        }
        return NumberNullSafeLimits.builder()
                .max(max)
                .min(min)
                .build();
    }

    public ArrayNullSafeLimits getArrayNullSafeLimits(int minDefault, int maxDefault, ArrayLimiter limiter, RandomUtils randomUtils) {
        long min = minDefault;
        long max = maxDefault;
        Integer length = null;
        if(limiter != null) {
            if(limiter.getMin() != null) {
                min = limiter.getMin();
            }
            if(limiter.getMax() != null) {
                max = limiter.getMax();
            }
            if(limiter.getLength() != null) {
                length = limiter.getLength();
            }
        }
        if(length == null) {
            length = randomUtils.getRandomIntWithinRange(min, max);
        }

        return ArrayNullSafeLimits.builder()
                .length(length)
                .max(max)
                .min(min)
                .build();
    }

    public static CollectionNullSafeLimits getCollectionNullSafeLimits(int minDefault, int maxDefault, CollectionLimiter limiter, RandomUtils randomUtils) {
        long min = minDefault;
        long max = maxDefault;
        if(limiter != null) {
            if(limiter.getMin() != null) {
                min = limiter.getMin();
            }
            if(limiter.getMax() != null) {
                max = limiter.getMax();
            }
            if(limiter.getSize() != null) {
                min = limiter.getSize();
                max = limiter.getSize();
            }
            if(limiter.getSize() != null) {
                min = limiter.getSize();
                max = limiter.getSize();
            }
        }
        long size = randomUtils.getRandomLongWithinRange(min,max);

        return CollectionNullSafeLimits.builder()
                .max(max)
                .min(min)
                .size(size)
                .build();
    }
}
