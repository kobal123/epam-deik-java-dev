package com.epam.training.ticketservice.core.user;

import com.epam.training.ticketservice.core.user.model.Role;
import com.epam.training.ticketservice.core.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private UserDto loggedInUser = null;


    @Override
    public Optional<UserDto> login(String username, String password) {
        Optional<User> user = userRepository.findByNameAndPassword(username, password);
        if (user.isEmpty()) {
            return Optional.empty();
        }
        loggedInUser = convertToDto(user.get());
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

    private UserDto convertToDto(User user) {
        return new UserDto(user.getName(), user.getRoles());
    }

}




