package com.epam.training.ticketservice.core.room;

import com.epam.training.ticketservice.core.seat.SeatDto;

import java.util.List;
import java.util.Optional;

public interface RoomService {

    void createRoom(RoomDto room);

    void updateRoom(RoomDto room);

    void deleteRoomByName(String name);

    Optional<RoomDto> findByName(String name);

    List<RoomDto> getAllRooms();

    Optional<SeatDto> checkSeatsExistForRoom(String roomName, List<SeatDto> seats);

    void attachPriceComponent(String componentName, String room);
}
