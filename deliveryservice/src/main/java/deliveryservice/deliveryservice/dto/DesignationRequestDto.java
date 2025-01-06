package deliveryservice.deliveryservice.dto;

import jakarta.persistence.Column;

public class DesignationRequestDto {
    private String country;
    private String capitalCity;
    private String city;
    private String message;
    private int countryDistance;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCapitalCity() {
        return capitalCity;
    }

    public void setCapitalCity(String capitalCity) {
        this.capitalCity = capitalCity;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCountryDistance() {
        return countryDistance;
    }

    public void setCountryDistance(int countryDistance) {
        this.countryDistance = countryDistance;
    }
}
