package deliveryservice.deliveryservice.servicesproject.exceptions.exceptionfiles;

public class CartNotFount extends RuntimeException{
    public CartNotFount() {
    }

    public CartNotFount(String message) {
        super(message);
    }

    public CartNotFount(String message, Throwable cause) {
        super(message, cause);
    }

    public CartNotFount(Throwable cause) {
        super(cause);
    }

    public CartNotFount(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
