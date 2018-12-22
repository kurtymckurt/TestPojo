# TestPojoData

## Description
This project's sole purpose is to generate data
for pojos for testing within an integration testing
frameworks.  

For example, if you had a `Person` class that has
a lot of instance variables, it's a pain to generate 
that pojo with data when testing.  

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
    Person person = TestPojoData.
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
* Support DateTimes / Date
* Support collections
* Ranges on field values
    * I.E. int age field with value 434679691 may break in a service method