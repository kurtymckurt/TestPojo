package org.kurtymckurt.TestPojoData;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import org.kurtymckurt.TestPojoData.generators.Generator;
import org.kurtymckurt.TestPojoData.providers.Provider;
import org.kurtymckurt.TestPojoData.providers.ProviderFunction;

import java.util.List;

@Value
@Builder
public class PojoBuilderConfiguration {

    private Class<?> clazz;
    private ProviderFunction providerFunction;

    @Singular
    private List<Provider> providers;

    @Singular
    private List<Generator> generators;

}
