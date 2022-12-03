package com.epam.training.ticketservice.room;

import java.util.List;
import java.util.Optional;

public interface RoomService {

    void createRoom(RoomDto room);

    void updateRoom(RoomDto room);

    void deleteRoomByName(String name);

    Optional<RoomDto> findByName(String name);

    List<RoomDto> getAllRooms();

    void attachPriceComponent(String componentName, String room);
}
