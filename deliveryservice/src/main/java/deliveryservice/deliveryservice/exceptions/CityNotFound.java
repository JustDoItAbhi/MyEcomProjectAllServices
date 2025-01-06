package deliveryservice.deliveryservice.exceptions;

public class CityNotFound extends Throwable {

    public CityNotFound() {
    }

    public CityNotFound(String message) {
        super(message);
    }
}
