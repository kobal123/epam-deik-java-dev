package com.epam.training.ticketservice.user;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private Optional<User> currentUser = Optional.empty();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public boolean signInUser(String userName, String password) {
        User user = userRepository.findById(userName).orElseThrow(()-> new BadCredentialsException("No such username"));

        if(!password.equals(user.getPassword())){
            return false;
        }
        currentUser = Optional.of(user);
        return true;
    }

    public void signOutUser() {
        currentUser = Optional.empty();
    }

    public Optional<User> getCurrentUser() {
        return currentUser;
    }

    public boolean isUserLoggedIn() {
        return currentUser.isPresent();
    }

    private boolean currentUserHasAuthority(Role role) {
        return currentUser.isPresent() && currentUser.get().getRoles().contains(role);
    }
}
