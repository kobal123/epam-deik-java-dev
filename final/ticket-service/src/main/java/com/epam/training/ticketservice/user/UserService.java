package com.epam.training.ticketservice.user;

import java.util.Optional;

public interface UserService {


    //Optional<UserDto> loginPrivileged(String userName, String password);

    Optional<UserDto> login(String userName, String password);

    Optional<UserDto> logout();

    void register(String userName, String password);

    Optional<UserDto> describe();



}
