package com.ecommer.userservices.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ROLES")
public class Roles {// role entity
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long roleId;// auto incremented role id as primary key
    private String roleType;// role name

    public Roles(String roleType) {// constructor
        this.roleType = roleType;
    }

    public long getRoleId() {// getter
        return roleId;
    }

    public void setRoleId(long roleId) {// setter
        this.roleId = roleId;
    }

    public String getRoleType() {// getter
        return roleType;
    }

    public void setRoleType(String roleType) {// setter
        this.roleType = roleType;
    }

}
