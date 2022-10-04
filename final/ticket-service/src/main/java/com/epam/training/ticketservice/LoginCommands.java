package com.epam.training.ticketservice;

import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellComponent;

@ShellComponent
public class LoginCommands {


    @ShellMethod(value = "Sign in as a privileged user", key = "sign in privileged")
    public void signIn(String username,String password) {
    //TODO: implementation
    }

    @ShellMethod(value = "Gives information about the currently logged in account", key = "describe account")
    public String describeAccount() {
        //TODO: implementation
        return null;
    }

    @ShellMethod(value = "Sign out from current account", key = "sign out")
    public void signOut(String username,String password) {
        //TODO: implementation
    }

}