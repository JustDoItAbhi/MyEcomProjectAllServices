package deliveryservice.deliveryservice.servicesproject.entity;

import jakarta.persistence.Entity;

@Entity
public class UserResponseUpdatedEntity extends BaseModels{
private String upadatedUserEmail;
    private String customerName;
    private String customerPhone;
    private String customerHouseNumber;
    private String customerStreet;
    private String customerLandMark;
    private String customerCity;
    private String customerState;
    private String customerCountry;
    private int customerPostelCode;

    public String getUpadatedUserEmail() {
        return upadatedUserEmail;
    }

    public void setUpadatedUserEmail(String upadatedUserEmail) {
        this.upadatedUserEmail = upadatedUserEmail;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerHouseNumber() {
        return customerHouseNumber;
    }

    public void setCustomerHouseNumber(String customerHouseNumber) {
        this.customerHouseNumber = customerHouseNumber;
    }

    public String getCustomerStreet() {
        return customerStreet;
    }

    public void setCustomerStreet(String customerStreet) {
        this.customerStreet = customerStreet;
    }

    public String getCustomerLandMark() {
        return customerLandMark;
    }

    public void setCustomerLandMark(String customerLandMark) {
        this.customerLandMark = customerLandMark;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public String getCustomerState() {
        return customerState;
    }

    public void setCustomerState(String customerState) {
        this.customerState = customerState;
    }

    public String getCustomerCountry() {
        return customerCountry;
    }

    public void setCustomerCountry(String customerCountry) {
        this.customerCountry = customerCountry;
    }

    public int getCustomerPostelCode() {
        return customerPostelCode;
    }

    public void setCustomerPostelCode(int customerPostelCode) {
        this.customerPostelCode = customerPostelCode;
    }
}
