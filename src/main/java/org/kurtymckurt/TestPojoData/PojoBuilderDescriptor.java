package org.kurtymckurt.TestPojoData;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Value
@Builder
public class PojoBuilderDescriptor<T> {

    private Class<T> clazz;
}
