package deliveryservice.deliveryservice.servicesproject.dtos;

import deliveryservice.deliveryservice.servicesproject.entity.PaymentStatus;

public class DeliveryResponseDto {// DELIVERY RESPONSE DTO
    private long deliveryId;
    private long orderId;
    private PaymentStatus paymentStatus;
    private double amount;
    // GETTERS AND SETTERS
    public long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
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
