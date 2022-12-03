package com.epam.training.ticketservice.room;

import com.epam.training.ticketservice.pricecomponent.PriceComponent;
import com.epam.training.ticketservice.pricecomponent.PriceComponentRepository;
import com.epam.training.ticketservice.seat.SeatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final PriceComponentRepository priceComponentRepository;



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
    public Optional<RoomDto> findByName(String name) {
        Optional<Room> room = roomRepository.findByName(name);
        return room.map(this::convertRoomToDto);
    }

    @Override
    public List<RoomDto> getAllRooms() {
        return roomRepository.findAll()
                .stream()
                .map(this::convertRoomToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SeatDto> checkSeatsExistForRoom(String roomName, List<SeatDto> seats) {
        Room room = roomRepository.findByName(roomName)
                .orElseThrow(() -> new IllegalArgumentException("Room does not exist"));

        for (SeatDto seat : seats) {
            boolean rowCheck = (seat.getSeatRow() >= 1) && (seat.getSeatRow() <= room.getSeatRows());
            boolean colCheck = (seat.getSeatCol() >= 1) && (seat.getSeatCol() <= room.getSeatCols());
            if (!rowCheck || !colCheck) {
                return Optional.of(seat);
            }
        }
        return Optional.empty();
    }

    @Override
    public void attachPriceComponent(String componentName, String roomName) {
        Room room = roomRepository.findByName(roomName)
                .orElseThrow(() -> new IllegalArgumentException("Could not find room"));
        PriceComponent component = priceComponentRepository.findById(componentName)
                .orElseThrow(() -> new IllegalArgumentException("Could not find price component"));

        room.addPriceComponent(component);
        roomRepository.save(room);
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
