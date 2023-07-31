package com.example.zookeeperscheduler.exception;

public class DomainValidationException extends RuntimeException {
    public DomainValidationException() {
        super();
    }

    public DomainValidationException(String message) {
        super(message);
    }

    public DomainValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DomainValidationException(Throwable cause) {
        super(cause);
    }

    protected DomainValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
