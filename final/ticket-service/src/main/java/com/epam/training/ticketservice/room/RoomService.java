package com.epam.training.ticketservice.room;

import java.util.List;

public interface RoomService {

    void createRoom(RoomDto room);

    void updateRoom(RoomDto room);

    void deleteRoomByName(String name);

    List<RoomDto> getAllRooms();
}
