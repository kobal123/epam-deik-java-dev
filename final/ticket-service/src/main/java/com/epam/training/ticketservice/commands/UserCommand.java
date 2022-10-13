package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.security.SecurityContext;
import com.epam.training.ticketservice.user.UserService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class UserCommand {
    private final UserService userService;

    public UserCommand(UserService userService) {
        this.userService = userService;
    }

    @ShellMethod(value = "Sign in as a privileged user", key = "sign in privileged")
    public void signIn(String username,String password) {
        if(!userService.signInUser(username,password)){
            System.out.println("Login failed due to incorrect credentials");
        }
    }



    @ShellMethod(value = "Sign out from current account", key = "sign out")
    public void signOut() {
        userService.signOutUser();
    }

    @ShellMethod(value = "Gives information about the currently logged in account", key = "describe account")
    public String describeAccount() {
        if (SecurityContext.USER.isUserLoggedIn()) {
            return String.format("Signed in with privileged account '%s'",SecurityContext.USER.getUser().get().getName());
        } else {
            return "You are not signed in";
        }
    }
}
