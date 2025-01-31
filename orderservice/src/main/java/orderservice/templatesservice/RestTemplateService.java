package orderservice.templatesservice;

import orderservice.dtos.CartResposneDtos;
import orderservice.exceptions.SignUpException;
import orderservice.users.userdtos.UserResponseDto;

import java.util.List;

public interface RestTemplateService {//INTERFACE CLASS FOR STRATEGY AND SERVICE LAYER DESIGN PETTERN
    CartResposneDtos fetchProduct(long cartId);// GET PRODUCT FROM PRODUCT SERVICE
    UserResponseDto getUserById(String email)throws SignUpException;// GET USER BY EMAIL
    List<UserResponseDto> getAllUser();// GET ALL USERS OPTIONAL


}
