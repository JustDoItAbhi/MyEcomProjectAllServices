package deliveryservice.deliveryservice.servicesproject.entity;

import jakarta.persistence.Entity;

@Entity
public class Delivery extends BaseModels{// DELVIERY ENTITY
    private String orderId;
    private String paymentStatus;
    private double amount;
    // GETTERS AND SETTERS
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
