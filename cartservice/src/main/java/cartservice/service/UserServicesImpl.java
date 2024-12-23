package cartservice.service;

import cartservice.entity.UserDetails;
import cartservice.mapper.UserMapper;
import cartservice.repository.UserDetailsReposirtoy;
import cartservice.userdtos.UserResponseDto;
import cartservice.client.UserclientRestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class UserServicesImpl implements UserServices{
    private final UserDetailsReposirtoy userDetailsReposirtoy;
//    @Qualifier("userServiceTemplte")
    private final RestTemplate restTemplate;
//    @Qualifier( "userServiceRestClient")
//    private final RestClient restClient;
    private final UserclientRestTemplate userclientRestTemplate;

    public UserServicesImpl(RestTemplate restTemplate, UserclientRestTemplate userclientRestTemplate, UserDetailsReposirtoy userDetailsReposirtoy) {
        this.restTemplate = restTemplate;
        this.userclientRestTemplate = userclientRestTemplate;
        this.userDetailsReposirtoy = userDetailsReposirtoy;
    }

    @Override
    public  UserDetails createUser(String email) {
        Optional<UserDetails> existingUser = userDetailsReposirtoy.findByUserEmail(email);

        if (existingUser.isEmpty()){
            try {
                UserResponseDto responseDto =userclientRestTemplate.getUserById(email);
                if(responseDto==null){
                    throw new RuntimeException("NO SUCH USER EXISTS "+email);
                }
                userDetailsReposirtoy.save(UserMapper.fromEntity(responseDto));
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        }
        return existingUser.get();
    }

    @Override
    public boolean deleteUser(long id) {
        userDetailsReposirtoy.deleteById(id);
        return true;
    }

    @Override
    public List<UserDetails> getAllUsers() {
        List<UserResponseDto> response = userclientRestTemplate.getAllUser();

        if (response == null) {
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
    public String deleteIfEmailEquslToEmail() {
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
}

