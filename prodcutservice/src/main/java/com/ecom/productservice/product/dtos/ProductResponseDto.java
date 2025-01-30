package com.ecom.productservice.product.dtos;

import com.ecom.productservice.entity.Categoryes;


public class ProductResponseDto {// PRODUCT RESPONSE DTO
    private long id;
    private String name;
    private String description;
    private String brand ;
    private int price;
    private int  stock;
    private String image;
    private Categoryes categoryes;
// GETTERS AND SETTERS
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
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
