package org.kurtymckurt.TestPojo;

import lombok.Data;
import org.junit.jupiter.api.Test;
import org.kurtymckurt.TestPojo.generators.PostGenerator;
import org.kurtymckurt.TestPojo.limiters.Limiters;
import org.kurtymckurt.TestPojo.pojo.Car;

import static org.junit.jupiter.api.Assertions.*;

public class TestPostGenerators
{

    @Test
    public void testSimplePostGenerator() {
        Point build = TestPojo.builder(Point.class)
                .addPostGenerator("x", "anotherX",
                        (PostGenerator<Integer, Integer>) o -> o).build();
        assertEquals(build.x, build.anotherX);
    }
    @Test
    public void testComplexPostGenerator() {
        Car car = TestPojo.builder(Car.CarBuilder.class, Car::builder)
                .addLimiter("make", Limiters.stringLimiter().length(1).build())
                .addPostGenerator("make", "speedometer.speed",
                        (PostGenerator<String, Integer>) String::length).build().build();
        assertEquals(car.getMake().length(), car.getSpeedometer().getSpeed());
    }

    @Test
    public void testSimplePostGenerator2() {
        Car car = TestPojo.builder(Car.CarBuilder.class, Car::builder)
                .addPostGenerator("make", "model",
                        (PostGenerator<String, String>) o -> "tesla").build().build();
        assertEquals("tesla", car.getModel());
    }

    @Test
    public void testSimplePostGenerator3() {
        Car car = TestPojo.builder(Car.CarBuilder.class, Car::builder)
                .addLimiter("make", Limiters.stringLimiter().length(5).build())
                .addPostGenerator("make", "model",
                        (PostGenerator<String, String>) o -> o + "tesla").build().build();
        assertEquals(10, car.getModel().length());
        assertTrue(car.getModel().endsWith("tesla"));
    }

    @Test
    public void testSomethingPostGenerator() {
        Car car = TestPojo.builder(Car.CarBuilder.class, Car::builder)
                .addExcludedField("make")
                .addPostGenerator("make", "speedometer.speed",
                        (PostGenerator<String, Integer>) s -> 1).build().build();
        assertNull(car.getMake());
        assertEquals(0, car.getSpeedometer().getSpeed());
    }

    @Data
    public static class Point {
        int x;
        int y;
        int anotherX;
    }
}
