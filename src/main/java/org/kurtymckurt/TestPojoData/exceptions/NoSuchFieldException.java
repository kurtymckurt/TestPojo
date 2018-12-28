package org.kurtymckurt.TestPojoData.exceptions;

public class NoSuchFieldException extends RuntimeException {

    public NoSuchFieldException(String fieldName, Class clazz, Exception cause) {
        super("No such field[" + fieldName + "] for class[" + clazz.getName() + "]", cause );
    }

}
