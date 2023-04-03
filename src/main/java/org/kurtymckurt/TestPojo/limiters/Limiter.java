package org.kurtymckurt.TestPojo.limiters;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class Limiter {
   Integer length;  // Strings, Arrays
   Integer size;    // Collections
   Long min;     // Numbers
   Long max;     // Numbers
   String regex;  //For string
   List<String> potentialValues;
}
