package org.kurtymckurt.TestPojoData.generators.time;

import org.kurtymckurt.TestPojoData.generators.Generator;
import org.kurtymckurt.TestPojoData.util.RandomUtils;

import java.lang.reflect.Field;
import java.time.Instant;

public class InstantGenerator implements Generator {
   @Override
   public Object generate(Class<?> clazz, Field field) {
      return Instant.ofEpochMilli(Math.abs(RandomUtils.getRandomLongObject()));
   }

   @Override
   public boolean supportsType(Class<?> clazz) {
      return clazz.isAssignableFrom(Instant.class);
   }
}
