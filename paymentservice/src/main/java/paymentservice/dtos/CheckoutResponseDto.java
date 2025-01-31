package paymentservice.dtos;

import com.stripe.param.PaymentLinkCreateParams;

import java.util.List;

public class CheckoutResponseDto {// PAYMENT CHECKOUT RESPONSE DTO
    private final String status;
    private final String message;
    private final List<PaymentLinkCreateParams.LineItem> lineItems;
    private final String url;
 


    // Private constructor to enforce the use of the builder
    private CheckoutResponseDto(Builder builder) {
        this.status = builder.status;
        this.message = builder.message;
        this.lineItems = builder.lineItems;
        this.url = builder.url;

    }
    // GETTERS
    public String getStatus() {
        return status;
    }
    public String getMessage() {
        return message;
    }
    public List<PaymentLinkCreateParams.LineItem> getLineItems() {
        return lineItems;
    }
    public String getUrl() {
        return url;
    }
    // Static Builder class
    public static class Builder {
        private String status;
        private String message;
        private List<PaymentLinkCreateParams.LineItem> lineItems;
        private long orderId;
        private long cartId;
        private long price;
        private String url;
        //SETTERS
        public Builder setOrderId(long orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder setCartId(long cartId) {
            this.cartId = cartId;
            return this;
        }

        public Builder setPrice(long price) {
            this.price = price;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setLineItems(List<PaymentLinkCreateParams.LineItem> lineItems) {
            this.lineItems = lineItems;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public CheckoutResponseDto build() {
            return new CheckoutResponseDto(this);
        }
    }
}