package org.kurtymckurt.TestPojo.limiters;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class StringLimiter implements Limiter {
    Integer length;
    String regex;
    List<String> potentialValues;
    Long min;
    Long max;
    public static class StringLimiterBuilder {}
}
