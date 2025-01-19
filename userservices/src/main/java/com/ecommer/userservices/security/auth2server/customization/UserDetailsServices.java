package com.ecommer.userservices.security.auth2server.customization;

import com.ecommer.userservices.entity.Users;
import com.ecommer.userservices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServices implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users>savedUser=userRepository.findByUserEmail(username);
        if(!savedUser.isPresent()){
            throw new UsernameNotFoundException("USER NOT FOUND "+ username);
        }
        Users users=savedUser.get();

        return new CustomUsersDetals(users);
    }
}
