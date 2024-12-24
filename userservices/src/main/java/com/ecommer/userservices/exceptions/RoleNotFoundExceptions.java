package com.ecommer.userservices.exceptions;

public class RoleNotFoundExceptions extends RuntimeException {

    public RoleNotFoundExceptions() {
    }

    public RoleNotFoundExceptions(String message) {
        super(message);
    }

    public RoleNotFoundExceptions(String message, Throwable cause) {
        super(message, cause);
    }

    public RoleNotFoundExceptions(Throwable cause) {
        super(cause);
    }

    public RoleNotFoundExceptions(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
