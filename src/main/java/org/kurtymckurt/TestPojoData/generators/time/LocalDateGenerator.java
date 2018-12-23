package org.kurtymckurt.TestPojoData.generators.time;

import org.kurtymckurt.TestPojoData.generators.Generator;
import org.kurtymckurt.TestPojoData.util.RandomUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;

public class LocalDateGenerator implements Generator {
   @Override
   public Object generate(Class<?> clazz, Field field) {
      return RandomUtils.getRandomLocalDate();
   }

   @Override
   public boolean supportsType(Class<?> clazz) {
      return clazz.isAssignableFrom(LocalDate.class);
   }
}
