package com.epam.training.ticketservice.room;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }


    @Override
    public void createRoom(Room room) {
        Optional<Room> roomByName = roomRepository.findById(room.getName());
        if (roomByName.isPresent()) {
            throw new IllegalArgumentException("Room already exists");
        }
        roomRepository.save(room);
    }

    @Override
    public void updateRoom(Room room) {
        Optional<Room> roomByName = roomRepository.findById(room.getName());
        if (roomByName.isEmpty()) {
            throw new IllegalArgumentException("Room does not exists");
        }
        roomRepository.save(room);
    }

    @Override
    public void deleteRoomByName(String name) {
        Optional<Room> roomByName = roomRepository.findById(name);
        if (roomByName.isEmpty()) {
            throw new IllegalArgumentException("Room does not exists");
        }
        roomRepository.deleteById(name);
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }
}
