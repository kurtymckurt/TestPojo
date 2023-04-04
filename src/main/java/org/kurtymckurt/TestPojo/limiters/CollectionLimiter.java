package org.kurtymckurt.TestPojo.limiters;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class CollectionLimiter implements Limiter {
    Integer size;
    Long min;
    Long max;
}
