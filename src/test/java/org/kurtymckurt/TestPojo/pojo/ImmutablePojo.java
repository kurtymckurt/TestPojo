package org.kurtymckurt.TestPojo.pojo;

import lombok.Builder;
import lombok.Value;

import java.util.Date;
import java.util.List;

@Value
@Builder
public class ImmutablePojo {

    private final String name;
    private final String address;
    private final String interestingAttribute;
    private final Date birthday;
    private List<Integer> listOfNumbers;

}
