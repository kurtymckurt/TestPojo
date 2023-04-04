package org.kurtymckurt.TestPojo;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import org.kurtymckurt.TestPojo.generators.Generator;
import org.kurtymckurt.TestPojo.generators.PostGenerator;
import org.kurtymckurt.TestPojo.limiters.Limiter;
import org.kurtymckurt.TestPojo.providers.ProviderFunctionContainer;
import org.kurtymckurt.TestPojo.util.RandomUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Value
@Builder(toBuilder = true)
public class PojoBuilderConfiguration {

    Class<?> clazz;

    @Singular
    Map<Class, ProviderFunctionContainer> providerFunctions;

    @Singular
    List<Generator> generators;

    @Singular
    Map<String, Map<String, PostGenerator>> postGenerators;

    @Singular
    Map<String, Limiter> limiters;

    @Singular
    Set<String> excludedFields;

    RandomUtils randomUtils;

    boolean warnOnFieldNotExisting;

}
