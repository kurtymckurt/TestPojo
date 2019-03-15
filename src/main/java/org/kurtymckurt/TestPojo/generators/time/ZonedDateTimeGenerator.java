package org.kurtymckurt.TestPojo.generators.time;

import org.kurtymckurt.TestPojo.generators.Generator;
import org.kurtymckurt.TestPojo.limiters.Limiter;
import org.kurtymckurt.TestPojo.util.RandomUtils;

import java.lang.reflect.Field;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ZonedDateTimeGenerator implements Generator {
   @Override
   public Object generate(Class<?> clazz, Field field, Limiter limiter) {


      String[] zones = ZoneId.getAvailableZoneIds().toArray(new String[ZoneId.getAvailableZoneIds().size()]);
      //int year, int month, int dayOfMonth, int hour, int minute, int second
      ZonedDateTime zonedDateTime = ZonedDateTime.of(RandomUtils.getRandomLocalDateTime(), ZoneId.of(zones[RandomUtils.getRandomIntWithinRange(0, zones.length-1)]));
      return zonedDateTime;
   }

   @Override
   public boolean supportsType(Class<?> clazz) {
      return clazz.isAssignableFrom(ZonedDateTime.class);
   }
}
