package org.kurtymckurt.TestPojo;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import org.kurtymckurt.TestPojo.generators.Generator;
import org.kurtymckurt.TestPojo.limiters.Limiter;
import org.kurtymckurt.TestPojo.providers.ProviderFunction;

import java.util.List;
import java.util.Map;

@Value
@Builder
public class PojoBuilderConfiguration {

    private Class<?> clazz;
    @Singular
    private Map<Class, ProviderFunction> providerFunctions;

    @Singular
    private List<Generator> generators;

    @Singular
    private Map<String, Limiter> limiters;

}
