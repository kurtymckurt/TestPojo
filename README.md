# TestPojoData

## Description
This project's sole purpose is to generate data
for pojos for testing within an integration testing
frameworks.  

For example, if you had a `Person` class that has
a lot of instance variables, it's a pain to generate 
that pojo with data when testing.  

##Features
* Supports all primitives and Object number types
* Has support for Generators
    * This is how you would generate a single low level type
        * I.E. Date, DateTime, Integer, etc.
* Has support for providers
    * This is how you provide instances of a type if you dont have access to the no arg constructor
        * IE Immutable objects for example.
        
See test package for examples.

## Example:

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

## Future improvements
* Support DateTimes
* Fully Support collections
* Ranges on field values
    * I.E. int age field with value 434679691 may break in a service method