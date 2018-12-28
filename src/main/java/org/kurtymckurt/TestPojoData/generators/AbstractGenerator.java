package org.kurtymckurt.TestPojoData.generators;

import com.github.javafaker.Faker;

/***
 * Interface that will generate the classes that it supports.
 *
 */
public abstract class AbstractGenerator implements Generator{
    protected Faker faker;

    public AbstractGenerator () {
        faker = Faker.instance();
    }
}
