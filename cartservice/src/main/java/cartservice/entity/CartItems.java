package cartservice.entity;

import jakarta.persistence.Entity;

@Entity
public class CartItems extends BaseModels{// CART ITEMS ENTITY
    private long productId;
    private String productName;
    private int quantity;
    private double price;
    //NO ARGUMENT CONSTRUCTOR
    public CartItems() {
    }
// PARAMETERISE CONSTRUCTOR
    public CartItems(long productId, String productName, int quantity, double price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }
// GETTERS AND SETTERS
    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
