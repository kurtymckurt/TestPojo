package org.kurtymckurt.TestPojoData.pojo;

import lombok.Data;

@Data
public class Person {
    private String name;
    private String address;
    private int age;
    private Gender gender;
}
