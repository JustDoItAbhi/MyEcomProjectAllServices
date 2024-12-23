package cartservice.service;

import cartservice.entity.UserDetails;
import cartservice.userdtos.UserResponseDto;

import java.util.List;

public interface UserServices {
    UserDetails createUser(String email);
    boolean deleteUser(long id);
    List<UserDetails>getAllUsers();
    String deleteIfEmailEquslToEmail();
}
