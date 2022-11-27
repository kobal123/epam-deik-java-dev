package com.epam.training.ticketservice.room;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }


    @Override
    public void createRoom(RoomDto room) {
        Optional<Room> roomByName = roomRepository.findByName(room.getName());
        if (roomByName.isPresent()) {
            throw new IllegalArgumentException("Room already exists");
        }
        Room roomToSave = convertDtoToRoom(room);
        roomRepository.save(roomToSave);
    }

    @Override
    public void updateRoom(RoomDto room) {
        Room roomToUpdate = roomRepository.findByName(room.getName())
                .orElseThrow(() -> new IllegalArgumentException("Room does not exists"));

        roomToUpdate = updateRoomFromDto(roomToUpdate, room);
        roomRepository.save(roomToUpdate);
    }

    @Override
    public void deleteRoomByName(String name) {
        Room roomToDelete = roomRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Room does not exists"));
        roomRepository.deleteById(roomToDelete.getId());
    }

    @Override
    public List<RoomDto> getAllRooms() {
        return roomRepository.findAll()
                .stream()
                .map(this::convertRoomToDto)
                .collect(Collectors.toList());
    }

    private Room updateRoomFromDto(Room room, RoomDto dto) {
        room.setName(dto.getName());
        room.setSeatRows(dto.getSeatRows());
        room.setSeatCols(dto.getSeatCols());
        return room;
    }

    private RoomDto convertRoomToDto(Room room) {
        return new RoomDto(
                room.getName(),
                room.getSeatRows(),
                room.getSeatCols()
        );
    }

    private Room convertDtoToRoom(RoomDto dto) {
        return new Room(
                dto.getName(),
                dto.getSeatRows(),
                dto.getSeatCols()
        );
    }
}
