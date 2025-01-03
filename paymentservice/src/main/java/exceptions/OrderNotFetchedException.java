package exceptions;

public class OrderNotFetchedException extends Exception{
    public OrderNotFetchedException() {
    }

    public OrderNotFetchedException(String message) {
        super(message);
    }

    public OrderNotFetchedException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderNotFetchedException(Throwable cause) {
        super(cause);
    }

    public OrderNotFetchedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
