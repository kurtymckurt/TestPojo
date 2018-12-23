package org.kurtymckurt.TestPojoData.util;

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

    public static int getRandomIntWithinRange(int min, int max) {
        return min + random.nextInt((max - min) + 1);
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

    public static Float getRandomFloatObject() {
        return Float.valueOf(random.nextFloat());
    }

    public static Long getRandomLongObject() {
        return Long.valueOf(random.nextLong());
    }

    public static Short getRandomShortObject() {
        return Short.valueOf((short)random.nextInt(Short.MAX_VALUE));
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
