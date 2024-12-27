package orderservice.exceptions;

public class OrderCannotPLacedexception extends RuntimeException{
    public OrderCannotPLacedexception() {
    }

    public OrderCannotPLacedexception(String message) {
        super(message);
    }

    public OrderCannotPLacedexception(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderCannotPLacedexception(Throwable cause) {
        super(cause);
    }

    public OrderCannotPLacedexception(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
