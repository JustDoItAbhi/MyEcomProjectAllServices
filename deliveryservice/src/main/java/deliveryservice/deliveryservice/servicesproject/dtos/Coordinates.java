package deliveryservice.deliveryservice.servicesproject.dtos;

public class Coordinates {// CO-ORDINATES FOR LOCATION
    private double latitude;
    private double longitude;
    // CONSTRUCTOR
    public Coordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
    // GETTER AND SETTERS
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}

