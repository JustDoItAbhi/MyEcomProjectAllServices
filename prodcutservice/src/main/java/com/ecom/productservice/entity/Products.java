package com.ecom.productservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;



@Entity
@Table(name="Products")
public class Products extends BaseModels {// PRODUCT ENTITY
   private String name;// PRODUCT NAME
    private String description;// PRODUCT DESCRIPTION
    private String Brand ;// BRAND OF PRODUCT
    private int Price;// PRICE OF PRODUCT
    private int  Stock;// PRODUCT IN STOCK
    private String image;// IMAGE URL OF PRODUCT
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)// MANY TO ONE CARDINAITY ON LAZY LOADING WITH PRESIST IN DATABASE
    @JoinColumn(name = "categoryId")// JOIN BY COLUME CATEGORYiD
    @JsonIgnore// json ignore annotation to avoid over loading
    private Categoryes categoryes;
    // GETTERS AND SETTERS
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int stock) {
        Stock = stock;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Categoryes getCategoryes() {
        return categoryes;
    }

    public void setCategoryes(Categoryes categoryes) {
        this.categoryes = categoryes;
    }
}
