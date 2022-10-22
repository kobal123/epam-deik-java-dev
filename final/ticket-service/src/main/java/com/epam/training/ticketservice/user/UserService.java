package com.epam.training.ticketservice.user;

import com.epam.training.ticketservice.security.SecurityContext;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void signInPrivilegedUser(String userName, String password) {
        User user = validateCredentials(userName,password);


        if (!user.getRole().contains(Role.ADMIN)) {
            throw new UserPrivilegeException("Cannot sign in user as admin, username is: " + userName);
        }
        SecurityContext.USER.setUser(Optional.of(user));
    }

    public void signInBasicUser(String userName, String password) {
        User user = validateCredentials(userName,password);

        if (!user.getRole().contains(Role.USER)) {
            throw new UserPrivilegeException("Cannot sign in user a basic user, username is: " + userName);
        }
        SecurityContext.USER.setUser(Optional.of(user));
    }



    public void signOutUser() {
        SecurityContext.USER.setUser(Optional.empty());
    }


    public void registerUser(String userName, String password) {
        Optional<User> user = userRepository.findById(userName);

        if (user.isPresent()) {
            throw new BadCredentialsException("Username is taken");
        }

        User userToBeSaved = new User(userName,password, Set.of(Role.USER));
        userRepository.save(userToBeSaved);
    }

    private User validateCredentials(String userName, String password) {
        User user = userRepository.findById(userName)
                .orElseThrow(() -> new BadCredentialsException("No such username " + userName));

        if (!password.equals(user.getPassword())) {
            throw new BadCredentialsException("Password is not matching for user " + userName);
        }
        return user;
    }

}




