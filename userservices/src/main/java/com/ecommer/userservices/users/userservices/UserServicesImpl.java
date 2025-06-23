package com.ecommer.userservices.users.userservices;

import com.ecommer.userservices.entity.Roles;
import com.ecommer.userservices.entity.UserStatus;
import com.ecommer.userservices.entity.Users;
import com.ecommer.userservices.exceptions.*;

import com.ecommer.userservices.kafka.KafkaProducerClinet;
import com.ecommer.userservices.kafka.SendEmailDto;
import com.ecommer.userservices.repository.RoleRepository;
import com.ecommer.userservices.repository.UserRepository;
import com.ecommer.userservices.users.userdtos.*;
import com.ecommer.userservices.users.usermapper.UserMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServicesImpl implements UserServices { //SERVICE LAYER
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private KafkaProducerClinet kafkaProducerClinet;
    private ObjectMapper objectMapper;

    public UserServicesImpl(UserRepository userRepository, RoleRepository roleRepository,
                             BCryptPasswordEncoder passwordEncoder,
                            KafkaProducerClinet kafkaProducerClinet, ObjectMapper objectMapper) {// CONSTRUCTOR
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.kafkaProducerClinet = kafkaProducerClinet;
        this.objectMapper = objectMapper;
    }

    @Override
    public UserResponseDto signUp(SignUp signUp) throws JsonProcessingException {
        Optional<Users> existingUser=userRepository.findByUserEmail(signUp.getUserEmail());
        if(existingUser.isPresent()){// USER VALIDATION
            throw new UserAlreadyExists("PLEASE LOGIN -> USER "+signUp.getUserEmail()+" ALREADY EXITS");
        }
        Users users=new Users();
        List<Roles> rolesList = new ArrayList<>();
        for(String roles:signUp.getRoles()) {// ADDING ROLE
            Optional<Roles> savedRole = roleRepository.findByRoleType(roles);// CHECKING DATABASE IF ROLE EXISTS
            if (savedRole.isEmpty()) {// ROLE VALIDATION
                throw new RoleNotFoundExceptions("ROLE NOT FOUND " + signUp.getRoles());// THROW ERROR IF ROLE NOT VALID
            }
            savedRole.get().setRoleType(roles);
            rolesList.add(savedRole.get());
            roleRepository.save(savedRole.get());// SAVE DINAMICALLY USER WITH ROLE
        }
        users.setRolesList(rolesList);

        users.setUserName(signUp.getUserName());
//        users.setCreatedAt(LocalDateTime.now());
        users.setUserEmail(signUp.getUserEmail());
        users.setUserPassword(passwordEncoder.encode(signUp.getUserPassword()));
        users.setUserPhone(signUp.getUserPhone());
        users.setUserPostelCode(signUp.getPostelCode());
        users.setUserCity(signUp.getCity());
        users.setUserState(signUp.getState());
        users.setUserCountry(signUp.getCountry());
        users.setUserStatus(UserStatus.LOGIN);
        users.setUserHouseNumber(signUp.getUserHouseNumber());
        users.setUserLandMark(signUp.getUserLandMark());
        users.setUserStreet(signUp.getUserStreet());
        userRepository.save(users);// SAVE USER IN DATABASE
        SendEmailDto emailDto=new SendEmailDto();// KAFAK EMAIL NOTIFICATION
        emailDto.setTo(users.getUserEmail());
        emailDto.setFrom("Pattorney0@gmail.com");
        emailDto.setSubject("WELCOME TO E-COMMERCE SERVICE");
        emailDto.setBody("thanks for joining " +" ☻ "+
                users.getUserName()+"  "+
                users.getUserPhone()+"  "+
                users.getUserEmail()+"  "+
                users.getUserHouseNumber()+"  "+
                users.getUserStreet()+"  "+
                users.getUserLandMark()+"  "+
                users.getUserCity()+"  "+
                users.getUserState()+"  "+
                users.getUserCountry()+"  "+
                users.getUserPostelCode());
        kafkaProducerClinet.sendMessage("sendemail",
                objectMapper.writeValueAsString(emailDto));

        return UserMapper.fromEntity(users);
            }

    @Override
    public UserResponseDto logIn(Login login) {// LOGIN USER
       Optional<Users> savedUser=userRepository.findByUserEmail(login.getUserEmail());
       if(!savedUser.isPresent()){// USER VALIDATION
           throw  new UsernameNotFoundException("USER NOT FOUND "+ login.getUserEmail());
       }
       if(!savedUser.get().getUserEmail().equals(login.getUserEmail())){// PASSWORD VALIDATION
           throw  new UsernameNotFoundException("USER NOT FOUND "+ login.getUserEmail());
       }
       Users users=savedUser.get();
       if(!passwordEncoder.matches(login.getUserPassword(),users.getUserPassword())){// PASSWORD ENCODED VALIDATION
           throw new UsernameNotFoundException("USER PASSWORD IS NOT CORRECT "+ login.getUserPassword());
       }
        users.setUserStatus(UserStatus.LOGIN);
       userRepository.save(users);
//       users.setRolesList(users.getRolesList());
       SendEmailDto sendEmailDto=new SendEmailDto();
       sendEmailDto.setTo(users.getUserEmail());
        sendEmailDto.setFrom("Pattorney0@gmail.com");
        sendEmailDto.setSubject("YOU SUCESSFULLY LOGIN");
        sendEmailDto.setBody(users.getUserName()+" logged in "+ users.getUserEmail()+" by email "+ users.getUserPhone()+" by phone "
                + " this is your id "+users.getRolesList()+" ROLE ");// SEND EMAIL IF USER IS VALID
        return UserMapper.fromEntity(users);
    }

    @Override
    public String logOut(LogOut logOut) {// LOGOUT METHOD
        Optional<Users>savedUser=userRepository.findByUserEmail(logOut.getEmail());
        if(savedUser.isEmpty()){
            throw new UsernameNotFoundException("USER NOT FOUND "+ logOut.getEmail());
        }
        Users user=savedUser.get();
        // Check if userStatus is null to prevent NullPointerException
        if (user.getUserStatus() == null || !user.getUserStatus().equals(UserStatus.LOGIN)) {
            throw new UsernameNotFoundException("USER NOT LOGIN " + logOut.getEmail());
        }
        // Update the user status to LOGOUT
        user.setUserStatus(UserStatus.LOGOUT);
        userRepository.save(user);
        SecurityContextHolder.clearContext();
        return " WE WILL SEE YOU AGAIN ☻ BYE BYE";
    }

    @Override
    public List<UserResponseDto> getAllUsers() {// GET ALL USERS
        List<Users> user=userRepository.findAll();
        List<UserResponseDto>dtos=new ArrayList<>();
        for(Users users:user){
            dtos.add(UserMapper.fromEntity(users));
        }
        return dtos;
    }



    @Override
    public boolean deleteUser(long id) {// DELETE USER
        Users user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        // Clear roles association first manuelly
        user.getRolesList().clear();
        userRepository.save(user);
        userRepository.deleteById(id);
        return true;
    }

    @Override
    public UserResponseDto getById(String email) throws SignUpUserException {// GET USER BY EMAIL
        Optional<Users>existingUser=userRepository.findByUserEmail(email);
        if(existingUser.isEmpty()){
            throw new SignUpUserException("PLEASE SIGN  UP "+email);
        }
        return UserMapper.fromEntity(existingUser.get());
    }

    @Override
    public UserResponseDto updateUser(String email, UpdateUserRequestDto dto) {// UPDATE USER
        Optional<Users> existingUser = userRepository.findByUserEmail(dto.getUserEmail());
        if (existingUser.isEmpty()) {
            throw new UserNotFoundException("DEAR CUSTOMER PLEASE SIGN UP FIRST " + dto.getUserName());
        }
        Users users = existingUser.get();
        users.setUserName(dto.getUserName());
//        users.setUserEmail(dto.getUserEmail());
        users.setUserPassword(passwordEncoder.encode(dto.getUserPassword()));
        users.setUserPhone(dto.getUserPhone());
//        users.setUpdatedAt(LocalDateTime.now());
        users.setUserPostelCode(dto.getPostelCode());
        users.setUserHouseNumber(dto.getUserHouseNumber());
        List<Roles>rolesList=new ArrayList<>();
        for(String roles:dto.getRoles()) {
            Optional<Roles> savedRole = roleRepository.findByRoleType(roles);
            if (savedRole.isEmpty()) {
                throw new RuntimeException("ROLE NOT FOUND " + dto.getRoles());
            }
            rolesList.add(savedRole.get());
        }
        users.setRolesList(rolesList);
        users.setUserLandMark(dto.getUserLandMark());
        users.setUserStreet(dto.getUserStreet());
        users.setUserCity(dto.getCity());
        users.setUserState(dto.getState());
        users.setUserCountry(dto.getCountry());
        userRepository.save(users);
        return UserMapper.fromEntity(users);
    }

    @Override
    public ResponseEntity<UserResponseDto> resetPassword(String email,String password) {// RESET PASSWORD IF USER FORGOT OR WANT TO CHANGE PASSWORD
        Optional<Users>existingUser=userRepository.findByUserEmail(email);
        if(existingUser.isEmpty()){
            throw new EmailNotFoundException("PLEASE ENTER CORRECT EMAIL OR SIGN UP "+email);
        }
        Users users=existingUser.get();
        if(passwordEncoder.matches(password, users.getUserPassword())){
           return new ResponseEntity<>( UserMapper.fromEntity(users),HttpStatus.ACCEPTED);
        }
        String newPassword=passwordEncoder.encode(password);
        users.setUserPassword(newPassword);
        return new ResponseEntity<>(UserMapper.fromEntity(users),HttpStatus.CREATED);
    }
}
