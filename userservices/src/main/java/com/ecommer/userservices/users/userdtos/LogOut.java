package com.ecommer.userservices.users.userdtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class LogOut {// LOGOUT REQUEST DTO
    private String email;
    //GETTERS ANDS SETTERS
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
