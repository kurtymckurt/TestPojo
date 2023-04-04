package org.kurtymckurt.TestPojo.util;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CollectionNullSafeLimits {
    public long size;
    public long min;
    public long max;
}
