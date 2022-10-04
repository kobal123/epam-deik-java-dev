package com.epam.training.ticketservice;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent

public class BasicCommands {


    @ShellMethod(value = "List all movies", key = "list movies")
    void listMovies(){

    }

    @ShellMethod(value = "List all rooms", key = "list rooms")
    void listRooms(){

    }
}
