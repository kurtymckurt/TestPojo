package org.kurtymckurt.TestPojoData.pojo;

import lombok.Data;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
public class TestPojo {

    //Objects
    private String name;
    private String address;

    //Test arrays
    private int[] somethingElse;

    private List<Integer> integers;
    private Set<Long> longs;
    private Collection<AnotherPojo> collectionOfPojos;

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
}
