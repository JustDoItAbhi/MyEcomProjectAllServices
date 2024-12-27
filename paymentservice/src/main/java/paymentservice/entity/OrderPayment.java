package paymentservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import paymentservice.dtos.OrderStatus;

import java.time.LocalDateTime;


@Entity
public class OrderPayment extends BaseModels{
    private long orderId;
    private LocalDateTime createdAt;
    private String userId;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private double price;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
