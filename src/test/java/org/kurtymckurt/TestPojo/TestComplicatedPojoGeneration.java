package org.kurtymckurt.TestPojo;

import lombok.Builder;
import lombok.Value;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import org.kurtymckurt.TestPojo.exceptions.NoSuchMethodException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class TestComplicatedPojoGeneration {

    @Test
    public void testComplexPojo() {
        Driver build = TestPojo.builder(Driver.class, Driver.builder()::build)
                .addProviderFunction(Car.builder()::build, Car.class)
                .build();

        assertTrue(build.cars.size() > 0);
    }

    @Test
    public void testComplexPojoUsingBuilderMethod() {
        Driver build = TestPojo.builder(Driver.class, Driver.builder()::build)
                .addProviderFunction(Car::builder, "build", Car.class)
                .build();

        assertTrue(build.cars.size() > 0);
    }

    @Test
    public void testComplexPojoUsingNonExistentBuilderMethod() {
        try {
            TestPojo.builder(Driver.class, Driver.builder()::build)
                    .addProviderFunction(Car::builder, "buildObject", Car.class)
                    .build();
            fail("should've thrown an exception.");
        } catch(NoSuchMethodException e) {
            //good
        }
    }


    @Value
    @Builder
    private static class Driver {

        Set<Car> cars;
        List<Car> oldCars;
    }

    @Value
    @Builder
    private static class Car {
        String make;
        String model;
    }
}
