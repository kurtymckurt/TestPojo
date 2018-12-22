package org.kurtymckurt.TestPojoData;

import lombok.Builder;
import lombok.Value;
import org.kurtymckurt.TestPojoData.generators.Generator;

import java.util.List;

@Value
@Builder
public class PojoBuilderDescriptor{

    private Class<?> clazz;
    private List<Generator> customGeneratorList;

}
