package com.ecommer.userservices.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Entity
@Table(name = "ROLES")
public class Roles extends BaseModels{
    private String roleType;
    @ManyToOne(fetch = FetchType.EAGER)
    private Users usersList;

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public Users getUsersList() {
        return usersList;
    }

    public void setUsersList(Users usersList) {
        this.usersList = usersList;
    }
}
