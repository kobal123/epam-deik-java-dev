package com.epam.training.ticketservice.room;

import com.epam.training.ticketservice.movie.Movie;
import com.epam.training.ticketservice.pricecomponent.PriceComponent;
import com.epam.training.ticketservice.pricecomponent.PriceComponentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @Test
    void testGetAllRooms() {
    }

    @Test
    void updateRoom() {
    }

    @Test
    void testDeleteRoomByNameShouldThrowIllegalArgumentExceptionWhenRoomDoesNotExists() {
        // given
        Mockito.when(roomRepository.findByName("room")).thenReturn(Optional.empty());

        // when
        // then
        assertThrows(IllegalArgumentException.class,
                () -> underTest.deleteRoomByName("room"));
    }

    @Test
    void testCreateRoomShouldThrowIllegalArgumentExceptionWhenRoomAlreadyExists() {
        // given
        Room room = new Room();
        RoomDto roomDto = new RoomDto("room", 10, 10);
        Mockito.when(roomRepository.findByName("room")).thenReturn(Optional.of(room));

        // when
        // then
        assertThrows(IllegalArgumentException.class,
                () -> underTest.createRoom(roomDto));
        Mockito.verify(roomRepository).findByName("room");
    }

    @Test
    void testUpdateRoomShouldThrowIllegalArgumentExceptionWhenRoomDoesNotExists() {
        // given
        RoomDto roomDto = new RoomDto("room", 10, 10);
        Mockito.when(roomRepository.findByName("room")).thenReturn(Optional.empty());

        // when
        // then
        assertThrows(IllegalArgumentException.class,
                () -> underTest.updateRoom(roomDto));
        Mockito.verify(roomRepository).findByName("room");
    }

    @Test
    void testCreateRoomShouldCallRoomRepositoryWhenInputIsValid() {
        // given

        RoomDto roomDto = new RoomDto("room", 10, 10);
        Mockito.when(roomRepository.findByName("room")).thenReturn(Optional.empty());

        // when
        underTest.createRoom(roomDto);
        // then

        Mockito.verify(roomRepository).findByName("room");
        Mockito.verify(roomRepository).save(new Room("room", 10, 10));

    }

    @Test
    void testUpdateRoomShouldCallRoomRepositoryWhenInputIsValid() {
        // given
        Room room = new Room();
        RoomDto roomDto = new RoomDto("room", 10, 10);
        Mockito.when(roomRepository.findByName("room")).thenReturn(Optional.of(room));

        // when
        underTest.updateRoom(roomDto);
        // then

        Mockito.verify(roomRepository).findByName("room");
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
}