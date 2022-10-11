package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.room.Room;
import com.epam.training.ticketservice.room.RoomService;
import com.epam.training.ticketservice.user.UserService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
public class RoomCommand {
    private final RoomService roomService;
    private final UserService userService;

    public RoomCommand(RoomService roomService, UserService userService) {
        this.roomService = roomService;
        this.userService = userService;
    }


    @ShellMethod(value = "Create a new room", key = "create room")
    void createRoom(String name, int numOfRows, int numOfCols) {
        Room room = new Room(name,numOfRows,numOfCols);
        roomService.createRoom(room);
    }

    @ShellMethod(value = "Update a room", key = "update room")
    void updateRoom(String name, int numOfRows, int numOfCols) {
        Room room = new Room(name,numOfRows,numOfCols);
        roomService.updateRoom(room);

    }

    @ShellMethod(value = "Delete a room", key = "delete room")
    void deleteRoom(String name){
        roomService.deleteRoomByName(name);
    }

    @ShellMethod(value = "List all rooms", key = "list rooms")
    void listRooms() {
        List<Room> rooms = roomService.getAllRooms();
        if (rooms.isEmpty()) {
            System.out.println("There are no rooms at the moment");
        } else {
            String format = "Room %s with %d seats, %d rows and %d columns";
            rooms.forEach(room -> System.out.println(String.format(format, room.getName(), room.getCapacity(), room.getSeatRows(), room.getSeatCols())));
        }
    }

}
