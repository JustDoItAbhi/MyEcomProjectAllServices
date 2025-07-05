package deliveryservice.deliveryservice.servicesproject.template;

import deliveryservice.deliveryservice.servicesproject.dtos.CartResposneDtos;
import deliveryservice.deliveryservice.servicesproject.dtos.UserResponseDto;

public interface CallingServices {
    UserResponseDto getUser(String userId);
    CartResposneDtos fetchingFromCartServcie(long cartId);
}
