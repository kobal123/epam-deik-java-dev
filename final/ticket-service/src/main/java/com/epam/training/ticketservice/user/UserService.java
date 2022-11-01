package com.epam.training.ticketservice.user;

public interface UserService {


    public void signInPrivilegedUser(String userName, String password);

    public void signInBasicUser(String userName, String password);

    public void signOutUser();

    public void registerUser(String userName, String password);

}
