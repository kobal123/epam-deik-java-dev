package com.epam.training.ticketservice.user;

import com.epam.training.ticketservice.security.SecurityContext;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public boolean signInUser(String userName, String password) {
        User user = userRepository.findById(userName).orElseThrow(()-> new BadCredentialsException("No such username"));

        if(!password.equals(user.getPassword())){
            return false;
        }

        SecurityContext.USER.setUser(Optional.of(user));
        return true;
    }

    public void signOutUser() {
        SecurityContext.USER.setUser(Optional.empty());
    }





}




