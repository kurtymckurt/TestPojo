package org.kurtymckurt.TestPojoData;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import org.kurtymckurt.TestPojoData.generators.Generator;
import org.kurtymckurt.TestPojoData.limiters.Limiter;
import org.kurtymckurt.TestPojoData.providers.Provider;
import org.kurtymckurt.TestPojoData.providers.ProviderFunction;

import java.util.List;
import java.util.Map;

@Value
@Builder
public class PojoBuilderConfiguration {

    private Class<?> clazz;
    @Singular
    private Map<Class, ProviderFunction> providerFunctions;

    @Singular
    private List<Provider> providers;

    @Singular
    private List<Generator> generators;

    @Singular
    private Map<String, Limiter> limiters;

}
