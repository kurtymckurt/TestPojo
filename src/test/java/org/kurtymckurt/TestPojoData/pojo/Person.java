package org.kurtymckurt.TestPojoData.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Person {
    private String name;
    private String address;
    private State state;
    private int age;
    private Gender gender;
    private Date birthDate;
}
