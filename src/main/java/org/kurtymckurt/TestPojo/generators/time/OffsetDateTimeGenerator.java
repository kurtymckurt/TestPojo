package org.kurtymckurt.TestPojo.generators.time;

import org.kurtymckurt.TestPojo.generators.Generator;
import org.kurtymckurt.TestPojo.limiters.Limiter;
import org.kurtymckurt.TestPojo.util.RandomUtils;

import java.lang.reflect.Field;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class OffsetDateTimeGenerator implements Generator {
   @Override
   public Object generate(Class<?> clazz, Field field, Limiter limiter) {

      OffsetDateTime offsetDateTime = OffsetDateTime.of(
            RandomUtils.getRandomLocalDateTime(),
            ZoneOffset.ofHoursMinutesSeconds(
                  RandomUtils.getRandomIntWithinRange(1, 12),
                  RandomUtils.getRandomIntWithinRange(1,59),
                  RandomUtils.getRandomIntWithinRange(1, 59)));
      return offsetDateTime;
   }

   @Override
   public boolean supportsType(Class<?> clazz) {
      return clazz.isAssignableFrom(OffsetDateTime.class);
   }
}
