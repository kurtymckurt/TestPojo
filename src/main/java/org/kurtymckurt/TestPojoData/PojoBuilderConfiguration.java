package org.kurtymckurt.TestPojoData;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import org.kurtymckurt.TestPojoData.generators.Generator;

import java.util.List;

@Value
@Builder
public class PojoBuilderConfiguration {

    private Class<?> clazz;
    @Singular
    private List<Generator> generators;

}
