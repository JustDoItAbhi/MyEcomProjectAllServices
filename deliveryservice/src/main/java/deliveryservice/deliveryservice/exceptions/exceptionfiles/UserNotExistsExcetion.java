package deliveryservice.deliveryservice.exceptions.exceptionfiles;

public class UserNotExistsExcetion extends RuntimeException {


    public UserNotExistsExcetion() {
    }

    public UserNotExistsExcetion(String message) {
        super(message);
    }

    public UserNotExistsExcetion(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotExistsExcetion(Throwable cause) {
        super(cause);
    }

    public UserNotExistsExcetion(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
