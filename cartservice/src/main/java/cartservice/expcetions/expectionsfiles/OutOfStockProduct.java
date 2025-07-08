package cartservice.expcetions.expectionsfiles;

public class OutOfStockProduct extends RuntimeException{
    public OutOfStockProduct() {
    }

    public OutOfStockProduct(String message) {
        super(message);
    }

    public OutOfStockProduct(String message, Throwable cause) {
        super(message, cause);
    }

    public OutOfStockProduct(Throwable cause) {
        super(cause);
    }

    public OutOfStockProduct(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
