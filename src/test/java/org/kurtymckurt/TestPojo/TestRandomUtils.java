package org.kurtymckurt.TestPojo;

import org.junit.jupiter.api.Test;
import org.kurtymckurt.TestPojo.util.RandomUtils;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestRandomUtils {

    @Test
    public void testIntWithinRange() {
        long min = 1;
        long max = 10;
        int randomIntWithinRange = RandomUtils.getRandomIntWithinRange(min, max);
        assertTrue(randomIntWithinRange >= min && randomIntWithinRange <= max);
    }


    @Test
    public void testLongWithinRange() {
        long min = 1;
        long max = 100;
        long randomLongWithinRange = RandomUtils.getRandomLongWithinRange(min, max);
        assertTrue(randomLongWithinRange >= min && randomLongWithinRange <= max);
    }

    @Test
    public void testLongIsExactValue() {
        long min = 15;
        long max = 15;
        long randomLongWithinRange = RandomUtils.getRandomLongWithinRange(min, max);
        assertTrue(randomLongWithinRange == min);
    }
}
