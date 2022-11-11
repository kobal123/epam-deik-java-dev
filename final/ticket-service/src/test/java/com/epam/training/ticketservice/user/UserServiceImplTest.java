package com.epam.training.ticketservice.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.training.ticketservice.user.model.Role;
import com.epam.training.ticketservice.user.model.User;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

class UserServiceImplTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserService underTest = new UserServiceImpl(userRepository);

    @Test
    void testLoginShouldSetLoggedInUserWhenUsernameAndPasswordAreCorrect() {
        // Given
        User user = new User("user", "password", Set.of(Role.USER));
        Optional<User> expected = Optional.of(user);
        when(userRepository.findByNameAndPassword("user", "pass")).thenReturn(Optional.of(user));

        // When
        Optional<UserDTO> actual = underTest.login("user", "pass");

        // Then
        assertEquals(expected.get().getName(), actual.get().getName());
        assertEquals(expected.get().getRoles(), actual.get().getRoles());
        verify(userRepository).findByNameAndPassword("user", "pass");
    }

    @Test
    void testLoginShouldReturnOptionalEmptyWhenUsernameOrPasswordAreNotCorrect() {
        // Given
        Optional<UserDTO> expected = Optional.empty();
        when(userRepository.findByNameAndPassword("dummy", "dummy")).thenReturn(Optional.empty());

        // When
        Optional<UserDTO> actual = underTest.login("dummy", "dummy");

        // Then
        assertEquals(expected, actual);
        verify(userRepository).findByNameAndPassword("dummy", "dummy");
    }

    @Test
    void testLogoutShouldReturnOptionalEmptyWhenThereIsNoOneLoggedIn() {
        // Given
        Optional<UserDTO> expected = Optional.empty();

        // When
        Optional<UserDTO> actual = underTest.logout();

        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testLogoutShouldReturnThePreviouslyLoggedInUserWhenThereIsALoggedInUser() {
        // Given
        User user = new User("user", "password", Set.of(Role.USER));
        when(userRepository.findByNameAndPassword("user", "pass")).thenReturn(Optional.of(user));
        Optional<UserDTO> expected = underTest.login("user", "password");

        // When
        Optional<UserDTO> actual = underTest.logout();

        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testDescribeShouldReturnTheLoggedInUserWhenThereIsALoggedInUser() {
        // Given
        User user = new User("user", "password", Set.of(Role.USER));
        when(userRepository.findByNameAndPassword("user", "pass")).thenReturn(Optional.of(user));
        Optional<UserDTO> expected = underTest.login("user", "password");

        // When
        Optional<UserDTO> actual = underTest.describe();

        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testDescribeShouldReturnOptionalEmptyWhenThereIsNoOneLoggedIn() {
        // Given
        Optional<UserDTO> expected = Optional.empty();

        // When
        Optional<UserDTO> actual = underTest.describe();

        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testRegisterUserShouldCallUserRepositoryWhenTheInputIsValid() {
        // Given
        // When
        underTest.register("user", "pass");

        // Then
        verify(userRepository).save(new User("user", "pass", Set.of(Role.USER)));
    }
}