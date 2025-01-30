package com.ecom.productservice.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "category")
public class Categoryes extends BaseModels {// CATEGORY ENTITY
    @Column(name = "category_name")
    private String categoryName;// CATEGORY NAME
    @Column(name = "category_description")
    private String categoryDescription;// CATEGORY DESCRIPTTION
    @OneToMany(mappedBy = "categoryes", fetch = FetchType.LAZY, cascade = CascadeType.ALL)// ONE TO MANY MAPPING WITH LAZY LOADING AND DEFAULT CASCADE
    private List<Products> products;// LIST OF PRODUCTS
// GETTERS AND SETTERS
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }
}