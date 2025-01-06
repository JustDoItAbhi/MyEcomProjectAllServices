package deliveryservice.deliveryservice.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Destinations  extends BaseModels{
    @Column(name="country")
    private String country;
    @Column(name="capital_city")
    private String capitalCity;
    @Column(name="city")
    private String city;
    private String message;
    @Column(name="country_distance")
    private int countryDistance;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCountryDistance() {
        return countryDistance;
    }

    public void setCountryDistance(int countryDistance) {
        this.countryDistance = countryDistance;
    }

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


}
