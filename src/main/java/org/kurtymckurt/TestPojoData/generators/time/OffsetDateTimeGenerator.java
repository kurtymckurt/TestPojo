package org.kurtymckurt.TestPojoData.generators.time;

import org.kurtymckurt.TestPojoData.generators.Generator;
import org.kurtymckurt.TestPojoData.util.RandomUtils;

import java.lang.reflect.Field;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class OffsetDateTimeGenerator implements Generator {
   @Override
   public Object generate(Class<?> clazz, Field field) {

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
