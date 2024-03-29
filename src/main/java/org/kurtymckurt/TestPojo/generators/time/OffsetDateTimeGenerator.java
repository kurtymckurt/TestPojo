package org.kurtymckurt.TestPojo.generators.time;

import org.kurtymckurt.TestPojo.PojoBuilderConfiguration;
import org.kurtymckurt.TestPojo.generators.Generator;
import org.kurtymckurt.TestPojo.limiters.Limiter;
import org.kurtymckurt.TestPojo.util.RandomUtils;

import java.lang.reflect.Field;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class OffsetDateTimeGenerator implements Generator<OffsetDateTime> {
   @Override
   public OffsetDateTime generate(Class<?> clazz, Field field, Limiter limiter, PojoBuilderConfiguration pojoBuilderConfiguration) {
      RandomUtils randomUtils = pojoBuilderConfiguration.getRandomUtils();
      OffsetDateTime offsetDateTime = OffsetDateTime.of(
              randomUtils.getRandomLocalDateTime(),
            ZoneOffset.ofHoursMinutesSeconds(
                    randomUtils.getRandomIntWithinRange(1, 12),
                    randomUtils.getRandomIntWithinRange(1,59),
                    randomUtils.getRandomIntWithinRange(1, 59)));
      return offsetDateTime;
   }

   @Override
   public boolean supportsType(Class<?> clazz) {
      return clazz.isAssignableFrom(OffsetDateTime.class);
   }
}
