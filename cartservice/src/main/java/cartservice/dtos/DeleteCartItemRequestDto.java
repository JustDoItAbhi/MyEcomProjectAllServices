package cartservice.dtos;

public class DeleteCartItemRequestDto {// DELETE CART ITEAM REQUEST DTO
    private String userId;
    private long cartId;
// GETTERS AND SETTERS
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
    }
}
