package com.epam.training.ticketservice.user;

import com.epam.training.ticketservice.user.exception.UserPrivilegeException;
import com.epam.training.ticketservice.user.model.Role;
import com.epam.training.ticketservice.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private Optional<UserDTO> currentUser = Optional.empty();


    public Optional<UserDTO> loginPrivileged(String userName, String password) {
        Optional<User> user = userRepository.findByNameAndPassword(userName, password);

        if (user.isEmpty() || !user.get().getRoles().contains(Role.ADMIN)) {
            return Optional.empty();
        }
        currentUser = Optional.of(convertToDTO(user.get()));
        return currentUser;
    }

    public Optional<UserDTO> login(String userName, String password) {
        Optional<User> user = userRepository.findByNameAndPassword(userName, password);

        if (user.isEmpty() || !user.get().getRoles().contains(Role.USER)) {
            return Optional.empty();
        }
        currentUser = Optional.of(convertToDTO(user.get()));
        return currentUser;
    }


    public Optional<UserDTO> logout() {
        if (currentUser.isEmpty()) {
            return Optional.empty();
        } else {
            Optional<UserDTO> user = currentUser;
            currentUser = Optional.empty();
            return user;
        }
    }


    public void register(String userName, String password) {

        User userToBeSaved = new User(userName,password, Set.of(Role.USER));

        try {
            userRepository.save(userToBeSaved);
        } catch (DataIntegrityViolationException violationException) {

        }
    }

    @Override
    public Optional<UserDTO> describe() {
        return currentUser;
    }

    private UserDTO convertToDTO(User user){
        return new UserDTO(user.getName(), user.getRoles());
    }

}




