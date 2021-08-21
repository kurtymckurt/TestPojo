package org.kurtymckurt.TestPojo.util;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

public class RandomUtils {

    private final Random random;
    private final long seed;

    public RandomUtils(long seed) {
        this.seed = seed;
        random = new Random(seed);
    }

    public long getSeed() {
        return seed;
    }

    /***
     * Get random integer within a range exclusively.
     * @param max
     * @return
     */
    public int getRandomIntWithinRange(int max) {
        return random.nextInt(max);
    }

    public int getRandomIntWithinRange(long min, long max) {
        if(max > Integer.MAX_VALUE) max = Integer.MAX_VALUE;
        if(min < Integer.MIN_VALUE) min = Integer.MIN_VALUE;
        return (int)((random.nextDouble() * ((max - min) + 1)) + min);
    }

    public Double getRandomDoubleWithinRange(double min, double max) {
        return (random.nextDouble() * ((max - min) + 1)) + min;
    }

    public Float getRandomFloatWithinRange(long min, long max) {
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

    public Long getRandomLongObject() {
        return Long.valueOf(random.nextLong());
    }

    public Long getRandomLongWithinRange(long min, long max) {
        return (long)(random.nextDouble() * ((max - min) + 1)) + min;
    }

    public Short getRandomShortWithinRange(long min, long max) {
        if(max > Short.MAX_VALUE) max = Short.MAX_VALUE;
        if(min < Short.MIN_VALUE) min = Short.MIN_VALUE;
        return Short.valueOf((short)((random.nextDouble() * ((max - min) + 1)) + min));
    }

    public byte getRandomByte() {
        byte[] bytes = new byte[1];
        random.nextBytes(bytes);
        return bytes[0];
    }

    public Boolean getRandomBoolean() {
        return random.nextBoolean();
    }

    public Character getRandomCharacter() {
        String uuid = UUID.randomUUID().toString();
        return uuid.charAt(getRandomIntWithinRange(uuid.length()));
    }

    public LocalDate getRandomLocalDate() {
        return LocalDate.of(
              getRandomIntWithinRange(1990, 9999),
              getRandomIntWithinRange(1, 12),
              getRandomIntWithinRange(1, 28));
    }

    public LocalDateTime getRandomLocalDateTime() {
        return LocalDateTime.of(
              getRandomIntWithinRange(1990, 9999),
              getRandomIntWithinRange(1, 12),
              getRandomIntWithinRange(1, 28),
              getRandomIntWithinRange(1, 12),
              getRandomIntWithinRange(1,59),
              getRandomIntWithinRange(1, 59));
    }
}
