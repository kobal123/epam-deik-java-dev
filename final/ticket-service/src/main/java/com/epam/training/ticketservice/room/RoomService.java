package com.epam.training.ticketservice.room;

import java.util.List;

public interface RoomService {

    void createRoom(Room room);

    void updateRoom(Room room);

    void deleteRoomByName(String name);

    List<Room> getAllRooms();
}
