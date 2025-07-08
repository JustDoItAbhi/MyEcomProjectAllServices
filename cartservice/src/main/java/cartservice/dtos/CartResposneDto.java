package cartservice.dtos;

import cartservice.entity.CartStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CartResposneDto {
    private CartStatus cartStatus;
    private long cartId;
    private List<CartItemResponseDto> items = new ArrayList<>();
    private long total;
    private LocalDateTime cartCreatedTime;
    private int balanceStock;
}
