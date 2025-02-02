package com.ecommer.userservices.exceptions;

public class NotCorrectEmailProvidedException extends RuntimeException{

    public NotCorrectEmailProvidedException() {
    }

    public NotCorrectEmailProvidedException(String message) {
        super(message);
    }

    public NotCorrectEmailProvidedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotCorrectEmailProvidedException(Throwable cause) {
        super(cause);
    }

    public NotCorrectEmailProvidedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
