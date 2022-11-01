package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.room.Room;
import com.epam.training.ticketservice.room.RoomService;
import com.epam.training.ticketservice.security.UserContext;
import com.epam.training.ticketservice.user.model.Role;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;

@ShellComponent
public class RoomCommand {

    private final RoomService roomService;

    public RoomCommand(RoomService roomService) {
        this.roomService = roomService;
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(value = "Create a new room", key = "create room")
    void createRoom(String name, int numOfRows, int numOfCols) {
        Room room = new Room(name,numOfRows,numOfCols);
        roomService.createRoom(room);
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(value = "Update a room", key = "update room")
    void updateRoom(String name, int numOfRows, int numOfCols) {
        Room room = new Room(name,numOfRows,numOfCols);
        roomService.updateRoom(room);

    }
    @ShellMethodAvailability("isAdmin")
    @ShellMethod(value = "Delete a room", key = "delete room")
    void deleteRoom(String name) {
        roomService.deleteRoomByName(name);
    }

    @ShellMethod(value = "List all rooms", key = "list rooms")
    void listRooms() {
        List<Room> rooms = roomService.getAllRooms();
        if (rooms.isEmpty()) {
            System.out.println("There are no rooms at the moment");
        } else {
            String format = "Room %s with %d seats, %d rows and %d columns";
            rooms.forEach(room -> System.out.println(String.format(format,
                    room.getName(),
                    room.getCapacity(),
                    room.getSeatRows(),
                    room.getSeatCols())));
        }
    }


    public Availability isAdmin() {

        return UserContext.userHasRole(Role.ADMIN)
                ? Availability.available()
                : Availability.unavailable("User is not an admin");
    }
}
