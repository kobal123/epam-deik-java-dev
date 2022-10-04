package com.epam.training.ticketservice;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class AdminCommands {

    @ShellMethod(value = "Create a new movie", key = "create movie")
    void createMovie(String title, String genre, int screenTime){

    }

    @ShellMethod(value = "Update a movie", key = "update movie")
    void updateMovie(String title, String genre, int screenTime){

    }

    @ShellMethod(value = "Delete a movie by its name", key = "create movie")
    void deleteMovie(String title){

    }


    @ShellMethod(value = "Create a new room", key = "create room")
    void createRoom(String name, int numOfRows, int numOfCols){

    }

    @ShellMethod(value = "Update a room", key = "update room")
    void updateRoom(String name, int numOfRows, int numOfCols){

    }

    @ShellMethod(value = "Delete a room", key = "delete room")
    void deleteRoom(String name){

    }

    @ShellMethod(value = "Create a new screening", key = "create screening")
    void createScreening(String movieTitle, String roomName, String startTime){

    }

    @ShellMethod(value = "Update a screening", key = "update screening")
    void updateScreening(String movieTitle, String roomName, String startTime){

    }

    @ShellMethod(value = "Delete a screening", key = "delete screening")
    void deleteScreening(String movieTitle, String roomName, String startTime){

    }


}
