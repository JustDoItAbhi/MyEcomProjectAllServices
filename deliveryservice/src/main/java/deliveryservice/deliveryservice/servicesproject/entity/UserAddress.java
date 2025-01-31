package deliveryservice.deliveryservice.servicesproject.entity;

import jakarta.persistence.*;

@Entity
@Table(name="userAddres")
public class UserAddress extends BaseModels{// USER ENTITY
    private String userName;
    private String userPhone;
    private String userEmail;
    private String userPassword;
    private String userHouseNumber;
    private String userStreet;
    private String userLandMark;
    private String userCity;
    private String userState;
    private String userCountry;
    private int userPostelCode;
    private String message;
    private int countryDistance;
    private long cartId;
    private long totalAmount;
    // GETTERS AND SETTESR
    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
    }



    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserHouseNumber() {
        return userHouseNumber;
    }

    public void setUserHouseNumber(String userHouseNumber) {
        this.userHouseNumber = userHouseNumber;
    }

    public String getUserStreet() {
        return userStreet;
    }

    public void setUserStreet(String userStreet) {
        this.userStreet = userStreet;
    }

    public String getUserLandMark() {
        return userLandMark;
    }

    public void setUserLandMark(String userLandMark) {
        this.userLandMark = userLandMark;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public String getUserCountry() {
        return userCountry;
    }

    public void setUserCountry(String userCountry) {
        this.userCountry = userCountry;
    }

    public int getUserPostelCode() {
        return userPostelCode;
    }

    public void setUserPostelCode(int userPostelCode) {
        this.userPostelCode = userPostelCode;
    }
}
