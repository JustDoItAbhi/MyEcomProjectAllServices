package paymentservice.exceptions;

public class PaymentConfirmed extends RuntimeException{
    public PaymentConfirmed() {
    }

    public PaymentConfirmed(String message) {
        super(message);
    }

    public PaymentConfirmed(String message, Throwable cause) {
        super(message, cause);
    }

    public PaymentConfirmed(Throwable cause) {
        super(cause);
    }

    public PaymentConfirmed(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
