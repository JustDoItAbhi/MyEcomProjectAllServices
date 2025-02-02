package orderservice.services;

import orderservice.exceptions.CannotFetchDataFromUserService;
import orderservice.exceptions.SignUpException;
import orderservice.repositorties.UserDetailsReposirtoy;
import orderservice.templatesservice.RestTemplateServiceImpl;
import orderservice.entity.UserDetails;
import orderservice.users.userdtos.UserResponseDto;
import orderservice.users.usermapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service// MANAGED BY SPRING SERVICE BEAN ANNOTATION
public class UserServicesImpl implements UserServices{// USER SERVICE IMPLEMENTATION
    private final UserDetailsReposirtoy userDetailsReposirtoy;// DECLARATION OF USER REPOSITORY
    private final RestTemplateServiceImpl userclientRestTemplate;
// DEPENDENCY INJECTION
    public UserServicesImpl(UserDetailsReposirtoy userDetailsReposirtoy, RestTemplateServiceImpl userclientRestTemplate) {
        this.userDetailsReposirtoy = userDetailsReposirtoy;
        this.userclientRestTemplate = userclientRestTemplate;
    }

    @Override
    public UserDetails createUser(String email) {//CREATE USER
        Optional<UserDetails> existingUser = userDetailsReposirtoy.findByUserEmail(email);// if use already registered in system
        if (existingUser.isEmpty()){
                UserResponseDto responseDto =userclientRestTemplate.getUserById(email);// try fetch user from userservice
                if(responseDto.getUserEmail()==null){//if user is not in user service then throw exception and request for sign up
                    throw new SignUpException("PLEASE SIGN UP AS NEW USER "+ email);
                }
              return   userDetailsReposirtoy.save(UserMapper.fromEntity(responseDto));// other wise return user and saved in order service
            }

        return existingUser.get();// or else return user if user is present in order service
    }

    @Override
    public boolean deleteUser(long id) {//only admin use
        userDetailsReposirtoy.deleteById(id);
        return true;
    }

    @Override
    public List<UserDetails> getAllUsers() {//get all users if need only admin use
        List<UserResponseDto> response = userclientRestTemplate.getAllUser();

        if (response == null) {// VALIDATE USER
            throw new RuntimeException("LIST IS EMPTY ");
        }
            List<UserDetails> userDetailsList = new ArrayList<>();

            for (UserResponseDto dto : response) {
                userDetailsList.add(UserMapper.fromEntity(dto));
            }
            userDetailsReposirtoy.saveAll(userDetailsList);
            return userDetailsList;
    }

    @Override
    public String deleteIfEmailEquslToEmail() {// DELETE FROM DATABASE IF SAME USER EMAIL EXISTS MULTIPLE TIMES
        List<UserDetails> userDetails = userDetailsReposirtoy.findAll();
        Set<String> seenEmails = new HashSet<>(); // To track seen emails
        for (UserDetails user : userDetails) {
            String userEmail = user.getUserEmail();
            if (seenEmails.contains(userEmail)) {
                // If email already exists in the set, delete this user
                userDetailsReposirtoy.deleteById(user.getUserId());
                userDetailsReposirtoy.save(user);
            } else {
                // Otherwise, add the email to the set
                seenEmails.add(userEmail);
            }
        }

        return "EXTRA IDS DELETED";
    }
    @Override
    public UserDetails getUserByEmail(String userEmail) {// GET USER BY EMAIL
        Optional<UserDetails> exitingUser=userDetailsReposirtoy.findByUserEmail(userEmail);
        if(exitingUser.isEmpty()){
            throw new CannotFetchDataFromUserService("USER NOT FOUND "+userEmail);
        }
        return exitingUser.get();
    }
}

