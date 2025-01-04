package deliveryservice.deliveryservice.exceptions;

public class UserNotExistsExcetion extends Exception{
    public UserNotExistsExcetion() {
    }

    public UserNotExistsExcetion(String message) {
        super(message);
    }
}
