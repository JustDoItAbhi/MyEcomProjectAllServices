package deliveryservice.deliveryservice.servicesproject.entity;

import jakarta.persistence.Entity;

@Entity
public class Delivery extends BaseModels{// DELVIERY ENTITY
    private long cartId;
    private PaymentStatus paymentStatus;
    private double amount;
    private DeliveryStatus deliveryStatus;
    private String message;
    // GETTERS AND SETTERS


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
