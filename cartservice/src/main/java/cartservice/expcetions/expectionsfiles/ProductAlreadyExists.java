package cartservice.expcetions.expectionsfiles;

public class ProductAlreadyExists extends RuntimeException{
    public ProductAlreadyExists() {
    }

    public ProductAlreadyExists(String message) {
        super(message);
    }

    public ProductAlreadyExists(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductAlreadyExists(Throwable cause) {
        super(cause);
    }

    public ProductAlreadyExists(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
