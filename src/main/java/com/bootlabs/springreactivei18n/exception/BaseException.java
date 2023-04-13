package com.bootlabs.springreactivei18n.exception;

public class BaseException extends RuntimeException {
    public String[] args;
    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message);
    }
    public BaseException(String message, String ... args) {
        super(message);
        this.args = args;
    }
    public BaseException(Throwable throwable) {
        super(throwable);
    }

    public BaseException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
