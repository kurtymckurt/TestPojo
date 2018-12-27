package org.kurtymckurt.TestPojoData;

import org.junit.jupiter.api.Test;
import org.kurtymckurt.TestPojoData.pojo.ImmutablePojo;
import org.kurtymckurt.TestPojoData.pojo.Person;
import org.kurtymckurt.TestPojoData.pojo.TestPojo;
import org.kurtymckurt.TestPojoData.providers.Provider;

import static org.junit.jupiter.api.Assertions.*;

public class TestPojoDataTest {

    @Test
    public void testBasicPojo() {
        TestPojo testPojo = TestPojoData.builder(TestPojo.class).build();

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

        assertTrue(testPojo.getNavigableMap().size() > 0);
        assertTrue(testPojo.getQueue().size() > 0);
        assertTrue(testPojo.getDeque().size() > 0);
        assertTrue(testPojo.getNavigableSetOfPeople().size() > 0);
        assertTrue(testPojo.getMapOfIdentifiersToLong().size() > 0);
        assertTrue(testPojo.getMapOfLongToPersons().size() > 0);
        assertTrue(testPojo.getConcurrentMapOfIdentifiersToLong().size() > 0);

    }

    @Test
    public void realisticTest() {
        Person person = TestPojoData.builder(Person.class).build();
        assertNotNull(person.getGender());
        assertNotNull(person.getName());
        assertNotNull(person.getBirthDate());
        assertNotNull(person.getAddress());
        assertNotNull(person.getState());
        assertNotNull(person.getSomeDateTime());
    }

    @Test
    public void testImmutableObjectWithBuilderWithProviderFunction() {
        ImmutablePojo.ImmutablePojoBuilder immutablePojoBuilder = TestPojoData.builder(
                ImmutablePojo.ImmutablePojoBuilder.class, ImmutablePojo::builder).build();

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
    public void testImmutableObjectWithBuilderWithCustomProvider() {
        ImmutablePojo.ImmutablePojoBuilder immutablePojoBuilder = TestPojoData.builder(
              ImmutablePojo.ImmutablePojoBuilder.class).addProvider(new OurImmutableBuilderProvider()).build();

        ImmutablePojo immutablePojo = immutablePojoBuilder.build();

        //Lets make sure this immutable object has contents.
        assertNotNull(immutablePojo.getAddress());
        assertNotNull(immutablePojo.getBirthday());
        assertNotNull(immutablePojo.getInterestingAttribute());
        assertNotNull(immutablePojo.getListOfNumbers());
        assertTrue(immutablePojo.getListOfNumbers().size()>0);
        assertNotNull(immutablePojo.getName());
    }

    private static class OurImmutableBuilderProvider implements Provider {

        @Override
        public Object provide() {
            return ImmutablePojo.builder();
        }

        @Override
        public boolean supportsType(Class<?> clazz) {
            return clazz.isAssignableFrom(ImmutablePojo.ImmutablePojoBuilder.class);
        }
    }

}
