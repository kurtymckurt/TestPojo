package org.kurtymckurt.TestPojo.util;

import org.kurtymckurt.TestPojo.limiters.Limiter;

public class LimiterUtils {

    public static NullSafeLimits getNullSafeLimits(int minDefault, int maxDefault, Limiter limiter) {
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
            if(limiter.getSize() != null) {
                min = limiter.getSize();
                max = limiter.getSize();
            }
            if(limiter.getLength() != null) {
                length = limiter.getLength();
            }
            if(limiter.getSize() != null) {
                min = limiter.getSize();
                max = limiter.getSize();
            }
        }

        if(length == null) {
            length = RandomUtils.getRandomIntWithinRange(min, max);
        }
        long size = RandomUtils.getRandomLongWithinRange(min,max);

        return NullSafeLimits.builder()
                .length(length)
                .max(max)
                .min(min)
                .size(size)
                .build();
    }
}
