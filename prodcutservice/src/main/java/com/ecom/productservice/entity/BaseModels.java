package com.ecom.productservice.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;


@MappedSuperclass// SUPER CLASS ANNOTATION BY JPA
public abstract class BaseModels {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;// AUTO INCREMENTED ID
// GETTERS AND SETTERS
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
