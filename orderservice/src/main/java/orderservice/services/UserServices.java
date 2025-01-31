package orderservice.services;



import orderservice.entity.UserDetails;

import java.util.List;

public interface UserServices {//INTERFACE CLASS FOR STRATEGY AND SERVICE LAYER DESIGN PETTERN
    UserDetails createUser(String email);// CREATE A USER
    boolean deleteUser(long id);// DELETE A USER
    List<UserDetails>getAllUsers();// GET ALL USERS
    String deleteIfEmailEquslToEmail();// DELETE DUPLICATE USERS
    UserDetails getUserByEmail(String email);// GET USER BY EMAIL


}

