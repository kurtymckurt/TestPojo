package org.kurtymckurt.TestPojo.pojo;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

@Data
public class Person implements Comparable<Person>{
    private String name;
    private String address;
    private State state;
    private int age;
    private Gender gender;
    private Date birthDate;
    private LocalDateTime someDateTime;
    private Instant birthDateInstant;
    private ZonedDateTime birthDateZoneDateTime;
    private OffsetDateTime birthDateOffsetDateTime;

    @Override public int compareTo(Person o) {
        int result = -1;
        if(this.age == o.age) result = 0;
        else if(this.age > o.age) result  = 1;

        return result;
    }
}
