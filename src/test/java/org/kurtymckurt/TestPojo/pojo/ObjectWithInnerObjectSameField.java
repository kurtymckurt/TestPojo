package org.kurtymckurt.TestPojo.pojo;

import lombok.Data;

@Data
public class ObjectWithInnerObjectSameField {

    String v1;
    String v2;
    InnerObject innerObject;

    @Data
    public static class InnerObject {
        String v1;
        String whatever;
    }

}

