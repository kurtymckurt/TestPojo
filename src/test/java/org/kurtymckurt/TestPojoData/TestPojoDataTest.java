package org.kurtymckurt.TestPojoData;

import org.junit.jupiter.api.Test;
import org.kurtymckurt.TestPojoData.pojo.Person;
import org.kurtymckurt.TestPojoData.pojo.TestPojo;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestPojoDataTest {

    @Test
    public void testBasicPojo() {
        TestPojo testPojo = TestPojoData.builder(TestPojo.class).build();

        //Test objects
        assertNotNull(testPojo.getName());
        assertNotNull(testPojo.getAddress());

        assertNotNull(testPojo.getBooleanObjectValue());
        assertNotNull(testPojo.getByteObjectValue());
        assertNotNull(testPojo.getDoubleObjectValue());
        assertNotNull(testPojo.getIntObjectValue());
        assertNotNull(testPojo.getShortObjectValue());
        assertNotNull(testPojo.getLongObjectValue());

    }

    @Test
    public void realisticTest() {
        Person person = TestPojoData.builder(Person.class).build();
        System.out.println(person);
    }

}
