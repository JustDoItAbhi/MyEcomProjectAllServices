package com.ecommer.userservices.users.userdtos;

import com.ecommer.userservices.entity.Roles;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class UserResponseDto {
    private long userId;
    private String userName;
    private String userPhone;
    private String userEmail;
    private String userPassword;
    private List<String> rolesList;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    public List<String> getRolesList() {
        return rolesList;
    }

    public void setRolesList(List<String> rolesList) {
        this.rolesList = rolesList;
    }
//    private String userCity;
//    private String userState;
//    private String userCountry;
}