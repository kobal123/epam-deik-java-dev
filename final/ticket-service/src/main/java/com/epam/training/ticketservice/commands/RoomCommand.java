package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.room.Room;
import com.epam.training.ticketservice.room.RoomDto;
import com.epam.training.ticketservice.room.RoomService;
import com.epam.training.ticketservice.user.UserDto;
import com.epam.training.ticketservice.user.UserService;
import com.epam.training.ticketservice.user.model.Role;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;
import java.util.Optional;

@ShellComponent
public class RoomCommand {

    private final RoomService roomService;
    private final UserService userService;

    public RoomCommand(RoomService roomService, UserService userService) {
        this.roomService = roomService;
        this.userService = userService;
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(value = "Create a new room", key = "create room")
    void createRoom(String name, int numOfRows, int numOfCols) {
        RoomDto room = new RoomDto(name,numOfRows,numOfCols);
        roomService.createRoom(room);
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(value = "Update a room", key = "update room")
    void updateRoom(String name, int numOfRows, int numOfCols) {
        RoomDto room = new RoomDto(name,numOfRows,numOfCols);
        roomService.updateRoom(room);

    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(value = "Delete a room", key = "delete room")
    void deleteRoom(String name) {
        roomService.deleteRoomByName(name);
    }

    @ShellMethod(value = "List all rooms", key = "list rooms")
    void listRooms() {
        List<RoomDto> rooms = roomService.getAllRooms();
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
        Optional<UserDto> userDto = userService.describe();
        return userDto.isPresent() && userDto.get().getRoles().contains(Role.ADMIN)
                ? Availability.available()
                : Availability.unavailable("User is not an admin");
    }
}
