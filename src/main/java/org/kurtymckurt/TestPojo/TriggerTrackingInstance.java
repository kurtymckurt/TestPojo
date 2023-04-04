package org.kurtymckurt.TestPojo;

import lombok.Value;

import java.lang.reflect.Field;

@Value
public class TriggerTrackingInstance {
    Object instance;
    Field  result;
}
