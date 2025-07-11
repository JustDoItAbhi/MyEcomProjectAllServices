package orderservice.dtos;

import lombok.Getter;
import lombok.Setter;
import orderservice.configrations.CustomJwtAuthenticationConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestClient;

@Getter
@Setter
public class CartResposneDtos {// CART RESPONSE DTO
    private long cartId;
    private long total;

}
