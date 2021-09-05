package org.kurtymckurt.TestPojo.exceptions;

public class NoSuchMethodException extends RuntimeException {

    public NoSuchMethodException(String method, Class clazz, Exception cause) {
        super("No such method[" + method + "] for class[" + clazz.getName() + "]", cause );
    }

}
