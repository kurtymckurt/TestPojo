package org.kurtymckurt.TestPojo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kurtymckurt.TestPojo.exceptions.NoSuchFieldException;
import org.kurtymckurt.TestPojo.limiters.Limiter;
import org.kurtymckurt.TestPojo.pojo.Car;
import org.kurtymckurt.TestPojo.pojo.ImmutablePojo;
import org.kurtymckurt.TestPojo.pojo.Person;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestPojoTest {

    @Test
    public void testBasicPojo() {
        org.kurtymckurt.TestPojo.pojo.TestPojo testPojo = TestPojo.builder(org.kurtymckurt.TestPojo.pojo.TestPojo.class)
                .addLimiter(
                        "anotherPojo.size", Limiter.builder()
                                .min(0L)
                                .max(1000L)
                                .build()
                )
                .addLimiter("somethingElse",
                        Limiter.builder()
                            .min(0L)
                            .max(9000L)
                            .length(20)
                                .build())
                .build();

        //Test objects
        assertNotNull(testPojo.getName());
        assertNotNull(testPojo.getAddress());

        //Test objects
        assertNotNull(testPojo.getBooleanObjectValue());
        assertNotNull(testPojo.getByteObjectValue());
        assertNotNull(testPojo.getDoubleObjectValue());
        assertNotNull(testPojo.getIntObjectValue());
        assertNotNull(testPojo.getShortObjectValue());
        assertNotNull(testPojo.getLongObjectValue());
        assertNotNull(testPojo.getMapOfIdentifiersToLong());
        assertNotNull(testPojo.getMapOfLongToPersons());
        assertNotNull(testPojo.getConcurrentMapOfIdentifiersToLong());
        assertNotNull(testPojo.getNavigableSetOfPeople());
        assertNotNull(testPojo.getNavigableMap());
        assertNotNull(testPojo.getQueue());
        assertNotNull(testPojo.getDeque());
        assertNotNull(testPojo.getIterable());

        assertTrue(testPojo.getAnotherPojo().getSize() >= 0 && testPojo.getAnotherPojo().getSize() <= 1000);
        assertTrue(testPojo.getNavigableMap().size() > 0);
        assertTrue(testPojo.getQueue().size() > 0);
        assertTrue(testPojo.getDeque().size() > 0);
        assertTrue(testPojo.getNavigableSetOfPeople().size() > 0);
        assertTrue(testPojo.getMapOfIdentifiersToLong().size() > 0);
        assertTrue(testPojo.getMapOfLongToPersons().size() > 0);
        assertTrue(testPojo.getConcurrentMapOfIdentifiersToLong().size() > 0);

        assertTrue(testPojo.getSomethingElse().length == 20);
        for(int i = 0; i < testPojo.getSomethingElse().length; i++) {
            assertTrue(testPojo.getSomethingElse()[i] >= 0
                    && testPojo.getSomethingElse()[i] <= 9000);
        }

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
        assertTrue(person.getName().length() == 10);
        assertTrue(person.getAddress().length() == 20);
    }

    @Test
    public void testImmutableObjectWithBuilderWithProviderFunction() {
        ImmutablePojo.ImmutablePojoBuilder immutablePojoBuilder = TestPojo.builder(
                ImmutablePojo.ImmutablePojoBuilder.class, ImmutablePojo::builder).build();

        ImmutablePojo immutablePojo = immutablePojoBuilder.build();

        //Lets make sure this immutable object has contents.
        assertNotNull(immutablePojo.getAddress());
        assertNotNull(immutablePojo.getBirthday());
        assertNotNull(immutablePojo.getInterestingAttribute());
        assertNotNull(immutablePojo.getListOfNumbers());
        assertTrue(immutablePojo.getListOfNumbers().size() >= 0);
        assertNotNull(immutablePojo.getName());
    }

    @Test
    public void testExceptionThrownWhenLimiterClassIsIncorrect() {
        Assertions.assertThrows(NoSuchFieldException.class, () -> {
            Person p = TestPojo.builder(Person.class)
                    .addLimiter(
                            "age.doesn'texist", Limiter.builder().build()
                    )
                    .build();
        });
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
}
