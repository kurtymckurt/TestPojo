package org.kurtymckurt.TestPojo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kurtymckurt.TestPojo.exceptions.NoSuchFieldException;
import org.kurtymckurt.TestPojo.limiters.Limiter;
import org.kurtymckurt.TestPojo.pojo.Car;
import org.kurtymckurt.TestPojo.pojo.ImmutablePojo;
import org.kurtymckurt.TestPojo.pojo.Person;
import org.kurtymckurt.TestPojo.pojo.TestingPojo;

import static org.junit.jupiter.api.Assertions.*;

public class TestPojoTesting {

    @Test
    public void testBasicPojo() {
        TestingPojo testingPojo = TestPojo.builder(TestingPojo.class)
                .addLimiter(
                        "anotherPojo.size", Limiter.builder()
                                .min(0L)
                                .max(1000L)
                                .build())
                .addLimiter("anotherPojo.car.make", Limiter.builder()
                        .length(5)
                        .build())
                .addLimiter("somethingElse",
                        Limiter.builder()
                            .min(0L)
                            .max(9000L)
                            .length(20)
                                .build())
                .addExcludedField("ignoreMe")
                .addExcludedField("anotherPojo.car.speedometer")
                .addProviderFunction(() -> Car.builder().build(), Car.class)
                .build();

        //Test objects
        assertNotNull(testingPojo.getName());
        assertNotNull(testingPojo.getAddress());

        //Test objects
        assertNotNull(testingPojo.getBooleanObjectValue());
        assertNotNull(testingPojo.getByteObjectValue());
        assertNotNull(testingPojo.getDoubleObjectValue());
        assertNotNull(testingPojo.getIntObjectValue());
        assertNotNull(testingPojo.getShortObjectValue());
        assertNotNull(testingPojo.getLongObjectValue());
        assertNotNull(testingPojo.getMapOfIdentifiersToLong());
        assertNotNull(testingPojo.getMapOfLongToPersons());
        assertNotNull(testingPojo.getConcurrentMapOfIdentifiersToLong());
        assertNotNull(testingPojo.getNavigableSetOfPeople());
        assertNotNull(testingPojo.getNavigableMap());
        assertNotNull(testingPojo.getQueue());
        assertNotNull(testingPojo.getDeque());
        assertNotNull(testingPojo.getIterable());

        assertTrue(testingPojo.getAnotherPojo().getSize() >= 0 && testingPojo.getAnotherPojo().getSize() <= 1000);
        assertEquals(5, testingPojo.getAnotherPojo().getCar().getMake().length());
        assertNotNull(testingPojo.getNavigableMap());
        assertNotNull(testingPojo.getQueue());
        assertNotNull(testingPojo.getDeque());
        assertNotNull(testingPojo.getNavigableSetOfPeople());
        assertNotNull(testingPojo.getMapOfIdentifiersToLong());
        assertNotNull(testingPojo.getMapOfLongToPersons());
        assertNotNull(testingPojo.getConcurrentMapOfIdentifiersToLong());

        assertEquals(20, testingPojo.getSomethingElse().length);
        for(int i = 0; i < testingPojo.getSomethingElse().length; i++) {
            assertTrue(testingPojo.getSomethingElse()[i] >= 0
                    && testingPojo.getSomethingElse()[i] <= 9000);
        }

        assertNull(testingPojo.getIgnoreMe());
        assertNull(testingPojo.getAnotherPojo().getCar().getSpeedometer());
    }

    @Test
    public void realisticTest() {
        Person person = TestPojo.builder(Person.class)
                .addLimiter(
                        "age", Limiter.builder()
                                .min(0L)
                                .max(100L)
                                .build()
                )
                .addLimiter(
                        "name", Limiter.builder()
                                .length(10)
                                .build()
                )
                .addLimiter(
                        "address", Limiter.builder()
                        .length(20)
                        .build()
                )
                .build();
        assertNotNull(person.getGender());
        assertNotNull(person.getName());
        assertNotNull(person.getBirthDate());
        assertNotNull(person.getAddress());
        assertNotNull(person.getState());
        assertNotNull(person.getSomeDateTime());
        assertTrue(person.getAge() >= 0 && person.getAge() <= 100);
        assertEquals(10, person.getName().length());
        assertEquals(20, person.getAddress().length());
    }

    @Test
    public void testImmutableObjectWithBuilderWithProviderFunction() {
        ImmutablePojo.ImmutablePojoBuilder immutablePojoBuilder = TestPojo.builder(
                ImmutablePojo.ImmutablePojoBuilder.class, ImmutablePojo::builder).build();

        ImmutablePojo immutablePojo = immutablePojoBuilder.build();

        //Let's make sure this immutable object has contents.
        assertNotNull(immutablePojo.getAddress());
        assertNotNull(immutablePojo.getBirthday());
        assertNotNull(immutablePojo.getInterestingAttribute());
        assertNotNull(immutablePojo.getListOfNumbers());
        assertNotNull(immutablePojo.getName());
    }

    @Test
    public void testExceptionThrownWhenLimiterClassIsIncorrect() {
        Assertions.assertThrows(NoSuchFieldException.class, () -> TestPojo.builder(Person.class)
                .addLimiter(
                        "age.doesn'texist", Limiter.builder().build()
                )
                .build());
    }

    @Test
    public void testImmutableObjectWithBuilderWithCustomProvider() {
        ImmutablePojo.ImmutablePojoBuilder immutablePojoBuilder = TestPojo.builder(
              ImmutablePojo.ImmutablePojoBuilder.class).addProviderFunction(ImmutablePojo::builder, ImmutablePojo.ImmutablePojoBuilder.class).build();

        ImmutablePojo immutablePojo = immutablePojoBuilder.build();

        //Lets make sure this immutable object has contents.
        assertNotNull(immutablePojo.getAddress());
        assertNotNull(immutablePojo.getBirthday());
        assertNotNull(immutablePojo.getInterestingAttribute());
        assertNotNull(immutablePojo.getListOfNumbers());
        assertTrue(immutablePojo.getListOfNumbers().size()>0);
        assertNotNull(immutablePojo.getName());
    }


    @Test
    public void testLimitInnerPojoLimiters() {
        Car car = TestPojo.builder(Car.CarBuilder.class, Car::builder)
                .addLimiter("speedometer.speed",
                        Limiter.builder().min(0L).max(120L).build()).build().build();

        assertTrue(car.getSpeedometer().getSpeed() >= 0 && car.getSpeedometer().getSpeed() <= 120);
    }

    @Test
    public void testSeedGeneration() {
        final Car car1 = TestPojo.builder(Car.CarBuilder.class, Car::builder)
                .setRandomGeneratorSeed(1000L).build().build();

        final Car car2 = TestPojo.builder(Car.CarBuilder.class, Car::builder)
                .setRandomGeneratorSeed(1000L).build().build();

        assertEquals(car1, car2, "Same seed, same car");
    }

    @Test
    public void testSeedGeneration2() {
        final Person person1 = TestPojo.builder(Person.class)
                .setRandomGeneratorSeed(9999).build();

        final Person person2 = TestPojo.builder(Person.class)
                .setRandomGeneratorSeed(9999).build();

        assertEquals(person1, person2, "Same seed, same person");
    }
}
