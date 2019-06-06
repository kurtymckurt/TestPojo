package org.kurtymckurt.TestPojo.pojo;

import lombok.Data;

import java.util.*;
import java.util.concurrent.ConcurrentMap;

@Data
public class TestingPojo {

    //Objects
    private String name;
    private String address;

    //Test arrays
    private int[] somethingElse;

    private List<Integer> integers;
    private Set<Long> longs;
    private Collection<Person> collectionOfPeople;
    private Map<String, Long> mapOfIdentifiersToLong;
    private ConcurrentMap<String, Long> concurrentMapOfIdentifiersToLong;
    private Map<Long, Person> mapOfLongToPersons;
    private NavigableSet<Person> navigableSetOfPeople;
    private NavigableMap<Person, Long> navigableMap;
    private Queue<Person> queue;
    private Deque<Person> deque;
    private Iterable<Person> iterable;

    //Test Custom pojo
    private AnotherPojo anotherPojo;

    //Test primitives
    private short shortValue;
    private long longValue;
    private int intValue;
    private boolean booleanValue;
    private double doubleValue;
    private byte byteValue;

    //Test Number Objects
    private Short shortObjectValue;
    private Long longObjectValue;
    private Integer intObjectValue;
    private Boolean booleanObjectValue;
    private Double doubleObjectValue;
    private Byte byteObjectValue;

    private Integer ignoreMe;
}
