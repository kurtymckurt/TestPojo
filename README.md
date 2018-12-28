# TestPojoData

## Description
This project's sole purpose is to generate data
for pojos for testing within an integration testing
frameworks.  

For example, if you had a `Person` class that has
a lot of instance variables, it's a pain to generate 
that pojo with data when testing.  

You should only have to implement anything extra for extreme
cases where a no arg constructor isn't available or a generator
doesn't support the complex data type.

## Features
* Supports all primitives and Object number types
* Supports Enums and Arrays
* Recursive POJO support
* Has support for Generators
    * This is how you would generate a single low level type
        * I.E. Date, DateTime, Integer, etc.
* Has support for providers
    * This is how you provide instances of a type if you dont have access to the no arg constructor
        * I.E. Immutable objects for example.
* Has support for basic limiters
    * This is how you can limit how big collections get or ranges for numbers       
        
See test package for examples.

## Example:

Simple Example
``` java
public class Person {
  private String name;
  private String address;
  private int age;
  private Gender gender;
}

public enum Gender {
   Male, Female
}

@Test
public void testPojos {
    Person person = TestPojoData.builder(Person.class).build();
}
```

Output:
```
Person(name=a42c539a-f85d-4745-87ab-5ee6714f6377, 
address=1a941c89-d481-468d-8336-17dd8e890e91, 
age=434679691,
gender=Male)
```

## Providers

There are circumstances in which TestPojoData may not be able to instantiate a new instance of the object passed to the builder.  In this situation, we can pass a provider through to give the engine a way to generate the classes as needed.

In the example below, we have an Immutable class that requires a builder in order to create.  Therefore, we wrote a provider that calls the method in order to generate a builder whenever the engine needs one.  Then, the engine will provide us with a builder with the filled in classes.  We may then modify and build to get our pojo.


This example uses a provider function to provide the way to create the pojo instance if necessary.
```java
@Value
@Builder
public class ImmutablePojo {

    private final String name;
    private final String address;
    private final String interestingAttribute;
    private final Date birthday;
    private List<Integer> listOfNumbers;
}

@Test
public void testOurImmutabilePojo (){
   ImmutablePojo pojo = TestPojoData.builder(
         ImmutablePojoBuilder.class, ImmutablePojo::builder())
      .build()
   .build();
}

```



This example, shows how you can do it by implementing a provider globally for pojos that do not have a no arg constructor.
```java
@Value
@Builder
public class ImmutablePojo {

    private final String name;
    private final String address;
    private final String interestingAttribute;
    private final Date birthday;
    private List<Integer> listOfNumbers;
}

@Test
public void testOurImmutabilePojo (){
   ImmutablePojo pojo = TestPojoData.builder(ImmutablePojoBuilder.class)
      .addProvider(new OurImmutableBuilderProvider())
      .build()
   .build();
}

private static class OurImmutableBuilderProvider implements Provider {
   @Override
   public Object provide() {
      return ImmutablePojo.builder();
   }

   @Override
    public boolean supportsType(Class<?> clazz) {
      return clazz.isAssignableFrom(ImmutablePojo.ImmutablePojoBuilder.class);
   }
}
```

## Generators
Generators are the core classes that provide the "random" values that fill in the datatypes.  You'll find a variety of already provided generators for Integer, Long, Float, Short, etc.  There are also generators provided for Collections.

You may provide custom generators through the TestPojoDataBuilder in order to supply custom datatypes that aren't already provided.

For example, the core TestPojoData doesn't provide a Generator for JodaTime classes.  This is because TestPojoData doesn't want a 3rd party dependency of JodaTime.

Each generator will be passed a limiter when executed so that it may honor these limits if applicable. See Limiter section for more details.

Example of how to use a custom generator
```java

@Test
public void realisticTest() {
    Person person = TestPojoData.builder(Person.class
    ).addCustomGenerator(new JodaTimeGenerator())  //Here's our custom generator
    .build();
    
   assertNotNull(person.getSomeDateTime());
}

private static class JodaTimeGenerator implements Generator {
   @Override
   public Object generate(Class<?> clazz, Field field, Limiter limiter) {
      return DateTime.now();
   }

   @Override
   public boolean supportsType(Class<?> clazz) {
      return clazz.isAssignableFrom(DateTime.class); 
   }
}

```


## Limiters

Limiters allow precise control over ranges or sizes of fields. They're intended to be simple and straight forward. It maybe as complicated as they will get for simplicities sake.

For numbers, you may specify:
* Min
* Max

For collections, you can specify:
* Min
    * Min for randomly choosing a size
* Max 
    * Max for randomly choosing a size
* Size
    * Used for explicitly setting a size

For strings, you can specify:
* length

For example, what if our pojo is a person and their age should reasonably be between 1 and 100? See the example below:

```java

@Test
public void realisticTest() {
    Person person = TestPojoData.builder(Person.class)
    .addLimiter(
            "age", 
            Limiter.builder()
                .min(1L)
                .max(100L)
            .build())
    .build();
}

```

Here, you can see that we're limiting the field "age" between 1 and 100. 

#### Complex Types
Limiters support limiting fields on objects within our pojo.

The example shows a Car below that also has a Speedometer.  We can limit the speedometer's speed by using the java class hierarchy notation "speedometer.speed" 

```java
@Value
@Builder
public class Car {
    private String make;
    private String model;
    private Speedometer speedometer;
}

@Data
public class Speedometer {
    private int speed;
}

public class TestCar {
    @Test
    public void testLimitInnerPojoLimiters() {
        Car car = TestPojoData.builder(Car.CarBuilder.class, Car::builder)
                .addLimiter("speedometer.speed",
                        Limiter.builder().min(0L).max(120L).build()).build().build();

        assertTrue(car.getSpeedometer().getSpeed() >= 0 && car.getSpeedometer().getSpeed() <= 120);
    }
}

```

#### Exception

If there is a problem with the name of the variable when specifying a limiter, we will fail fast.  The library will throw a NoSuchFieldException and state which class it believes should've had that field.

```java
public class TestCar {
    @Test
    public void testLimitInnerPojoLimiters() {
        Car car = TestPojoData.builder(Car.CarBuilder.class, Car::builder)
                .addLimiter("speedometer.rateOfSpeed",  // incorrect field name
                        Limiter.builder().min(0L).max(120L).build()).build().build();

        assertTrue(car.getSpeedometer().getSpeed() >= 0 && car.getSpeedometer().getSpeed() <= 120);
    }
}
```

This incorrect field name will produce the following stacktrace.
```java
org.kurtymckurt.TestPojoData.exceptions.NoSuchFieldException: No such field[rateOfSpeed] for class[org.kurtymckurt.TestPojoData.pojo.Speedometer]

	at org.kurtymckurt.TestPojoData.PojoBuilder.verifyAndBuildLimitersMapHelper(PojoBuilder.java:69)
	at org.kurtymckurt.TestPojoData.PojoBuilder.verifyAndBuildLimitersMapHelper(PojoBuilder.java:64)
	at org.kurtymckurt.TestPojoData.PojoBuilder.verifyAndBuildLimitersMap(PojoBuilder.java:55)
	at org.kurtymckurt.TestPojoData.PojoBuilder.<init>(PojoBuilder.java:36)
    ...
```

## Future improvements
* Support DateTimes
* Fully Support collections
* Ranges on field values
    * I.E. int age field with value 434679691 may break in a service method