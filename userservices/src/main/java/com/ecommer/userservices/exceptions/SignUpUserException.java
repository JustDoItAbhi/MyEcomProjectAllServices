package com.ecommer.userservices.exceptions;

public class SignUpUserException extends RuntimeException{

    public SignUpUserException() {
    }

    public SignUpUserException(String message) {
        super(message);
    }

    public SignUpUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public SignUpUserException(Throwable cause) {
        super(cause);
    }

    public SignUpUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
