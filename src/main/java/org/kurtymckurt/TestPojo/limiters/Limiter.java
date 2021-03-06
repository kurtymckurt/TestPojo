package org.kurtymckurt.TestPojo.limiters;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Limiter {
   private Integer length;  // Strings, Arrays
   private Integer size;    // Collections
   private Long min;     // Numbers
   private Long max;     // Numbers
   private String regex;  //For string generation
}
