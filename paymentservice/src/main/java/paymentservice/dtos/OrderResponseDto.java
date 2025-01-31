package paymentservice.dtos;


import org.springframework.security.core.userdetails.UserDetails;

public class OrderResponseDto {// ORDER RESPONSE DTO
    private long orderid;
    private long cartId;
    private OrderStatus  orderStatus;
    private long price;
// GETTERS AND SETTERS
    public long getOrderid() {
        return orderid;
    }

    public void setOrderid(long orderid) {
        this.orderid = orderid;
    }

    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

}
