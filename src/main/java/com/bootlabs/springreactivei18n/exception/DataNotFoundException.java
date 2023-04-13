package com.bootlabs.springreactivei18n.exception;

public class DataNotFoundException extends BaseException {
    public DataNotFoundException() {
        super();
    }

    public DataNotFoundException(String message) {
        super(message);
    }
    public DataNotFoundException(String message, String ... args) {
        super(message, args);
    }
    public DataNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
