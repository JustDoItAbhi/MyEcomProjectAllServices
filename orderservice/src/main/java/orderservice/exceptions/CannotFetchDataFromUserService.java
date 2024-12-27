package orderservice.exceptions;

public class CannotFetchDataFromUserService extends RuntimeException{
    public CannotFetchDataFromUserService() {
    }

    public CannotFetchDataFromUserService(String message) {
        super(message);
    }

    public CannotFetchDataFromUserService(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotFetchDataFromUserService(Throwable cause) {
        super(cause);
    }

    public CannotFetchDataFromUserService(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
