package org.kurtymckurt.TestPojo.generators.time;

import org.kurtymckurt.TestPojo.generators.Generator;
import org.kurtymckurt.TestPojo.limiters.Limiter;
import org.kurtymckurt.TestPojo.util.RandomUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;

public class LocalDateGenerator implements Generator {
   @Override
   public Object generate(Class<?> clazz, Field field, Limiter limiter) {
      return RandomUtils.getRandomLocalDate();
   }

   @Override
   public boolean supportsType(Class<?> clazz) {
      return clazz.isAssignableFrom(LocalDate.class);
   }
}