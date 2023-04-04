package org.kurtymckurt.TestPojo.util;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ArrayNullSafeLimits {
    public int length;
    public long min;
    public long max;
}
