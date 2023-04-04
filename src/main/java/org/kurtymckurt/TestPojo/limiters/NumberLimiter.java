package org.kurtymckurt.TestPojo.limiters;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class NumberLimiter implements Limiter {
    Long min;
    Long max;
    public static class NumberLimiterBuilder {}
}
