package orderservice.services;



import orderservice.users.UserDetails;

import java.util.List;

public interface UserServices {
    UserDetails createUser(String email);
    boolean deleteUser(long id);
    List<UserDetails>getAllUsers();
    String deleteIfEmailEquslToEmail();
    UserDetails getUserById(String email);
}

