package org.kurtymckurt.TestPojo.generators.time;

import org.kurtymckurt.TestPojo.PojoBuilderConfiguration;
import org.kurtymckurt.TestPojo.generators.Generator;
import org.kurtymckurt.TestPojo.limiters.Limiter;
import org.kurtymckurt.TestPojo.util.RandomUtils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

public class LocalDateTimeGenerator implements Generator<LocalDateTime> {
   @Override
   public LocalDateTime generate(Class<?> clazz, Field field, Limiter limiter, PojoBuilderConfiguration pojoBuilderConfiguration) {
      return pojoBuilderConfiguration.getRandomUtils().getRandomLocalDateTime();
   }

   @Override
   public boolean supportsType(Class<?> clazz) {
      return clazz.isAssignableFrom(LocalDateTime.class);
   }
}
