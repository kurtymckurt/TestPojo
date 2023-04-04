package org.kurtymckurt.TestPojo.util;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class NumberNullSafeLimits {
    public long min;
    public long max;
}
