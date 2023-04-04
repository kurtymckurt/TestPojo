package org.kurtymckurt.TestPojo.limiters;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ArrayLimiter implements Limiter {
    Integer length;
    Long min;
    Long max;
}
