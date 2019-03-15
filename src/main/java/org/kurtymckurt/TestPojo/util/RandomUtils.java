package org.kurtymckurt.TestPojo.util;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class RandomUtils {

    private static final SecureRandom random = new SecureRandom();

    /***
     * Get random integer within a range exclusively.
     * @param max
     * @return
     */
    public static int getRandomIntWithinRange(int max) {
        return random.nextInt(max);
    }

    public static int getRandomIntWithinRange(long min, long max) {
        if(max > Integer.MAX_VALUE) max = Integer.MAX_VALUE;
        if(min < Integer.MIN_VALUE) min = Integer.MIN_VALUE;
        return (int)((random.nextDouble() * ((max - min) + 1)) + min);
    }

    public static int getRandomInt() {
        return getRandomIntWithinRange(Integer.MAX_VALUE);
    }

    public static String getRandomString() {
        return UUID.randomUUID().toString();
    }

    public static Double getRandomDoubleObject() {
        return Double.valueOf(random.nextDouble());
    }

    public static Double getRandomDoubleWithinRange(double min, double max) {
        return (random.nextDouble() * ((max - min) + 1)) + min;
    }

    public static Float getRandomFloatObject() {
        return Float.valueOf(random.nextFloat());
    }

    public static Float getRandomFloatWithinRange(long min, long max) {
        float floatMin;
        float floatMax;
        if(max > Float.MAX_VALUE) {
            floatMin = Float.MAX_VALUE;
        } else {
            floatMin = min;
        }
        if(min < Float.MIN_VALUE) {
            floatMax = Float.MIN_VALUE;
        } else {
            floatMax = max;
        }
        return (random.nextFloat() * ((floatMax - floatMin) + 1)) + floatMin;
    }

    public static Long getRandomLongObject() {
        return Long.valueOf(random.nextLong());
    }

    public static Long getRandomLongWithinRange(long min, long max) {
        return (long)(random.nextDouble() * ((max - min) + 1)) + min;
    }

    public static Short getRandomShortObject() {
        return Short.valueOf((short)random.nextInt(Short.MAX_VALUE));
    }

    public static Short getRandomShortWithinRange(long min, long max) {
        if(max > Short.MAX_VALUE) max = Short.MAX_VALUE;
        if(min < Short.MIN_VALUE) min = Short.MIN_VALUE;
        return Short.valueOf((short)((random.nextDouble() * ((max - min) + 1)) + min));
    }

    public static byte getRandomByte() {
        byte[] bytes = new byte[1];
        random.nextBytes(bytes);
        return bytes[0];
    }

    public static Boolean getRandomBoolean() {
        return random.nextBoolean();
    }

    public static Character getRandomCharacter() {
        String uuid = UUID.randomUUID().toString();
        return uuid.charAt(getRandomIntWithinRange(uuid.length()));
    }


    public static LocalDate getRandomLocalDate() {
        return LocalDate.of(
              RandomUtils.getRandomIntWithinRange(1990, 9999),
              RandomUtils.getRandomIntWithinRange(1, 12),
              RandomUtils.getRandomIntWithinRange(1, 28));
    }

    public static LocalDateTime getRandomLocalDateTime() {
        return LocalDateTime.of(
              RandomUtils.getRandomIntWithinRange(1990, 9999),
              RandomUtils.getRandomIntWithinRange(1, 12),
              RandomUtils.getRandomIntWithinRange(1, 28),
              RandomUtils.getRandomIntWithinRange(1, 12),
              RandomUtils.getRandomIntWithinRange(1,59),
              RandomUtils.getRandomIntWithinRange(1, 59));
    }
}
