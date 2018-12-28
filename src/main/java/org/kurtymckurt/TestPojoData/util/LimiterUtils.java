package org.kurtymckurt.TestPojoData.util;

import org.kurtymckurt.TestPojoData.limiters.Limiter;

public class LimiterUtils {

    public static NullSafeLimits getNullSafeLimits(int minDefault, int maxDefault, int lengthDefault, Limiter limiter) {
        long min = 1;
        long max = 100;
        long length = lengthDefault;

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

        long size = RandomUtils.getRandomLongWithinRange(min,max);

        return NullSafeLimits.builder()
                .length(length)
                .max(max)
                .min(min)
                .size(size)
                .build();
    }
}
