package com.epam.training.ticketservice.user;

import com.epam.training.ticketservice.user.model.Role;
import com.epam.training.ticketservice.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private UserDto loggedInUser = null;

/*
    public Optional<UserDto> loginPrivileged(String userName, String password) {
        Optional<User> user = userRepository.findByNameAndPassword(userName, password);

        if (user.isEmpty() || !user.get().getRoles().contains(Role.ADMIN)) {
            return Optional.empty();
        }
        loggedInUser = Optional.of(convertToDTO(user.get()));
        return loggedInUser;
    }*/


    @Override
    public Optional<UserDto> login(String username, String password) {
        Optional<User> user = userRepository.findByNameAndPassword(username, password);
        if (user.isEmpty()) {
            return Optional.empty();
        }
        loggedInUser = convertToDTO(user.get());
        return describe();
    }

    @Override
    public Optional<UserDto> logout() {
        Optional<UserDto> previouslyLoggedInUser = describe();
        loggedInUser = null;
        return previouslyLoggedInUser;
    }

    @Override
    public Optional<UserDto> describe() {
        return Optional.ofNullable(loggedInUser);
    }

    @Override
    public void register(String username, String password) {
        User user = new User(username, password, Set.of(Role.USER));
        userRepository.save(user);
    }

    private UserDto convertToDTO(User user){
        return new UserDto(user.getName(), user.getRoles());
    }

}




