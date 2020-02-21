package org.kurtymckurt.TestPojo;

import lombok.Builder;
import lombok.Value;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestComplicatedPojoGeneration {

    @Test
    public void testComplexPojo() {


        Driver build = TestPojo.builder(Driver.class, Driver.builder()::build)
                .addProviderFunction(Car.builder()::build, Car.class)
                .build();

        assertTrue(build.cars.size() > 0);
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
