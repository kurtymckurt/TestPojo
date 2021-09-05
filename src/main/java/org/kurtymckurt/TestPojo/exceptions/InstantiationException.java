package org.kurtymckurt.TestPojo.exceptions;

public class InstantiationException extends RuntimeException {
    public InstantiationException(Class clazz, Exception cause) {
        super("Could not instantiate instance of class[" + clazz.getName() + "]", cause );
    }
}
