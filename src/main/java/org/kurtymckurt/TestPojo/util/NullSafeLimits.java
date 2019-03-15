package org.kurtymckurt.TestPojo.util;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class NullSafeLimits {

    public int length;
    public long size;
    public long min;
    public long max;
}
