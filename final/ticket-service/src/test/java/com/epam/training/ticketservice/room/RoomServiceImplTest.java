package com.epam.training.ticketservice.room;

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
    }

    @Test
    void getAllRooms() {
    }
}