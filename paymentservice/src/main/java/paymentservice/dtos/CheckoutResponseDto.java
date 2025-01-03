package paymentservice.dtos;

import com.stripe.param.PaymentLinkCreateParams;

import java.util.List;

public class CheckoutResponseDto {
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
        private String url;

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