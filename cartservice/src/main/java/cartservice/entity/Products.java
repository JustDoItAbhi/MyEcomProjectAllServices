package cartservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="Products")
public class Products extends BaseModels {// PRODUCT ENTITY CLASS TO STORE PRODUCT IN DATABASE IF REUIRED
   private String name;
    private String description;
    private String Brand ;
   private int Price;
   private int  Stock;
    private String image;
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
}
