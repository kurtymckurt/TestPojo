package org.kurtymckurt.TestPojoData.pojo;

import lombok.Data;

@Data
public class TestPojo {

    //Objects
    private String name;
    private String address;

    //Test arrays
    private int[] somethingElse;

    //Test Custom pojo
    private SecondPojo secondPojo;

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
