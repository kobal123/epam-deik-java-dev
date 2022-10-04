package com.epam.training.ticketservice.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private User currentUser;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public boolean currentUserHasAuthority(Role role){
        return currentUser.getRoles().contains(role);
    }
}
