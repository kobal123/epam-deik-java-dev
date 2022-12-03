package com.epam.training.ticketservice.core.room;

import com.epam.training.ticketservice.core.room.Room;
import com.epam.training.ticketservice.core.room.RoomDto;
import com.epam.training.ticketservice.core.room.RoomRepository;
import com.epam.training.ticketservice.core.room.RoomServiceImpl;
import com.epam.training.ticketservice.core.pricecomponent.PriceComponent;
import com.epam.training.ticketservice.core.pricecomponent.PriceComponentRepository;
import com.epam.training.ticketservice.core.seat.SeatDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RoomServiceImplTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private PriceComponentRepository priceComponentRepository;

    @InjectMocks
    private RoomServiceImpl underTest;

    private static final Room ROOM = new Room("room", 10, 10);
    private static final RoomDto ROOM_DTO = new RoomDto("room", 10, 10);

    @Test
    void testGetAllRooms() {
    }

    @Test
    void updateRoom() {
    }

    @Test
    void testDeleteRoomByNameShouldThrowIllegalArgumentExceptionWhenRoomDoesNotExists() {
        // given
        Mockito.when(roomRepository.findByName(ROOM.getName())).thenReturn(Optional.empty());

        // when
        // then
        assertThrows(IllegalArgumentException.class,
                () -> underTest.deleteRoomByName(ROOM.getName()));
    }

    @Test
    void testCreateRoomShouldThrowIllegalArgumentExceptionWhenRoomAlreadyExists() {
        // given
        Mockito.when(roomRepository.findByName(ROOM.getName())).thenReturn(Optional.of(ROOM));

        // when
        // then
        assertThrows(IllegalArgumentException.class,
                () -> underTest.createRoom(ROOM_DTO));
        Mockito.verify(roomRepository).findByName(ROOM.getName());
    }

    @Test
    void testUpdateRoomShouldThrowIllegalArgumentExceptionWhenRoomDoesNotExists() {
        // given
        Mockito.when(roomRepository.findByName(ROOM.getName())).thenReturn(Optional.empty());

        // when
        // then
        assertThrows(IllegalArgumentException.class,
                () -> underTest.updateRoom(ROOM_DTO));
        Mockito.verify(roomRepository).findByName(ROOM.getName());
    }

    @Test
    void testCreateRoomShouldCallRoomRepositoryWhenInputIsValid() {
        // given

        Mockito.when(roomRepository.findByName(ROOM_DTO.getName())).thenReturn(Optional.empty());

        // when
        underTest.createRoom(ROOM_DTO);
        // then

        Mockito.verify(roomRepository).findByName(ROOM_DTO.getName());
        Mockito.verify(roomRepository).save(new Room("room", 10, 10));

    }

    @Test
    void testUpdateRoomShouldCallRoomRepositoryWhenInputIsValid() {
        // given
        Mockito.when(roomRepository.findByName(ROOM.getName())).thenReturn(Optional.of(ROOM));

        // when
        underTest.updateRoom(ROOM_DTO);
        // then

        Mockito.verify(roomRepository).findByName(ROOM_DTO.getName());
        Mockito.verify(roomRepository).save(new Room("room", 10, 10));

    }


    @Test
    void testAttachPriceComponentShouldCallRepositoryWhenInputIsValid() {
        // given
        Room room = new Room("name", 10, 10);
        PriceComponent priceComponent = new PriceComponent("component", 1500);
        Mockito.when(priceComponentRepository.findById(priceComponent.getName()))
                .thenReturn(Optional.of(priceComponent));
        Mockito.when(roomRepository.findByName(room.getName()))
                .thenReturn(Optional.of(room));


        //when
        underTest.attachPriceComponent(priceComponent.getName(), room.getName());
        //then
        Mockito.verify(roomRepository).findByName(room.getName());
        Mockito.verify(roomRepository)
                .save(room);
        assertTrue(room.getPriceComponents().contains(priceComponent));
    }

    @Test
    void checkSeatsExistForRoomShouldThrowIllegalArgumentExceptionWhenRoomDoesNotExist() {
        //given
        String roomName = "room";
        List<SeatDto> seats = List.of(
                new SeatDto(1,1),
                new SeatDto(2,1),
                new SeatDto(3,3)
        );
        Mockito.when(roomRepository.findByName(roomName)).thenReturn(Optional.empty());


        //when
        //then
        assertThrows(IllegalArgumentException.class,
                () -> underTest.checkSeatsExistForRoom(roomName, seats));
    }

    @Test
    void checkSeatsExistForRoomShouldReturnSeatDtoWhenRowDoesNotExist() {
        //given
        String roomName = "room";
        SeatDto expected = new SeatDto(-1,3);
        List<SeatDto> seats = List.of(
                new SeatDto(1,1),
                new SeatDto(2,1),
                expected
        );
        Mockito.when(roomRepository.findByName(roomName)).thenReturn(Optional.of(ROOM));


        //when
        Optional<SeatDto> actual = underTest.checkSeatsExistForRoom(roomName, seats);

        //then
        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void checkSeatsExistForRoomShouldReturnSeatDtoWhenColumnDoesNotExist() {
        //given
        String roomName = "room";
        SeatDto expected = new SeatDto(1,-3);
        List<SeatDto> seats = List.of(
                new SeatDto(1,1),
                expected,
                new SeatDto(2,1)
        );
        Mockito.when(roomRepository.findByName(roomName)).thenReturn(Optional.of(ROOM));


        //when
        Optional<SeatDto> actual = underTest.checkSeatsExistForRoom(roomName, seats);

        //then
        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }


    @Test
    void checkSeatsExistForRoomShouldReturnSeatDtoWhenColumnAndRowDoesNotExist() {
        //given
        String roomName = "room";
        SeatDto expected = new SeatDto(-1,-3);
        List<SeatDto> seats = List.of(
                new SeatDto(1,1),
                expected,
                new SeatDto(2,1)
        );
        Mockito.when(roomRepository.findByName(roomName)).thenReturn(Optional.of(ROOM));

        //when
        Optional<SeatDto> actual = underTest.checkSeatsExistForRoom(roomName, seats);

        //then
        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void checkSeatsExistForRoomShouldReturnOptionalEmpty() {
        //given
        String roomName = "room";
        Optional<SeatDto> expected = Optional.empty();
        List<SeatDto> seats = List.of(
                new SeatDto(1,1),
                new SeatDto(2,1)
        );
        Mockito.when(roomRepository.findByName(roomName)).thenReturn(Optional.of(ROOM));


        //when
        Optional<SeatDto> actual = underTest.checkSeatsExistForRoom(roomName, seats);

        //then
        assertTrue(actual.isEmpty());
        assertEquals(expected, actual);
    }



}