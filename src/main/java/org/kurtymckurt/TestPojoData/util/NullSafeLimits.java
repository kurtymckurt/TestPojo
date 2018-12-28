package org.kurtymckurt.TestPojoData.util;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class NullSafeLimits {

    public long length;
    public long size;
    public long min;
    public long max;
}
