package org.kurtymckurt.TestPojo.exceptions;

public class IllegalAccessException extends RuntimeException {

    public IllegalAccessException(Class clazz, Exception cause) {
        super("Could not access a field for class[" + clazz.getName() + "]", cause );
    }

}
