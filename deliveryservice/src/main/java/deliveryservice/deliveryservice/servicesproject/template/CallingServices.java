package deliveryservice.deliveryservice.servicesproject.template;

import deliveryservice.deliveryservice.servicesproject.dto.CartResposneDtos;
import deliveryservice.deliveryservice.servicesproject.dto.UserResponseDto;

public interface CallingServices {
    UserResponseDto getUser(String userId);
    CartResposneDtos fetchingFromCartServcie(long cartId);
}
