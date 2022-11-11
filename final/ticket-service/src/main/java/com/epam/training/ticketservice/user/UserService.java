package com.epam.training.ticketservice.user;

import java.util.Optional;

public interface UserService {


    Optional<UserDTO> loginPrivileged(String userName, String password);

    Optional<UserDTO> login(String userName, String password);

    Optional<UserDTO> logout();

    void register(String userName, String password);

    Optional<UserDTO> describe();

}
