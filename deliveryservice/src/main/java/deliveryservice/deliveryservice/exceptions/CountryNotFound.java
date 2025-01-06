package deliveryservice.deliveryservice.exceptions;

public class CountryNotFound extends Throwable {

    public CountryNotFound() {
    }

    public CountryNotFound(String message) {
        super(message);
    }
}
