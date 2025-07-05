package deliveryservice.deliveryservice.servicesproject.dtos;

import deliveryservice.deliveryservice.servicesproject.entity.PaymentStatus;

public class DeliveryServiceNotification {// DELIVERY NOTIFICATION DTO
    private long orderId;
    private PaymentStatus paymentStatus;
    private double amount;

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
