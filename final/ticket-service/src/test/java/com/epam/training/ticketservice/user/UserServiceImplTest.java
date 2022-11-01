package com.epam.training.ticketservice.user;

import com.epam.training.ticketservice.screening.ScreeningServiceImpl;
import com.epam.training.ticketservice.user.exception.BadCredentialsException;
import com.epam.training.ticketservice.user.exception.UserPrivilegeException;
import com.epam.training.ticketservice.user.model.Role;
import com.epam.training.ticketservice.user.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl underTest;// = new ScreeningServiceImpl(formatter, screeningRepository,movieRepository );


    @Test
    void testSignInPrivilegedUserShouldThrowBadCredentialsExceptionOnWrongCredentials() {

        // given
        String username = "username";
        String password = "password";
        String wrongPassword = "password1";
        User user = new User(username, password, Set.of(Role.ADMIN) );
        Mockito.when(userRepository.findById(username)).thenReturn(Optional.of(user));

        // when
        // then
        assertThrows(BadCredentialsException.class,
                () -> underTest.signInPrivilegedUser(username,wrongPassword));
    }

    @Test
    void testSignInBasicUserShouldThrowBadCredentialsExceptionOnWrongCredentials() {
        // given
        String username = "username";
        String password = "password";
        String wrongPassword = "password1";
        User user = new User(username, password, Set.of(Role.USER) );
        Mockito.when(userRepository.findById(username)).thenReturn(Optional.of(user));

        // when
        // then
        assertThrows(BadCredentialsException.class,
                () -> underTest.signInBasicUser(username,wrongPassword));
    }

    @Test
    void testSignInBasicUserWithAdminAccountShouldThrowUserPrivilegeException() {
        // given
        String username = "username";
        String password = "password";
        User user = new User(username, password, Set.of(Role.ADMIN) );
        Mockito.when(userRepository.findById(username)).thenReturn(Optional.of(user));

        // when
        // then
        assertThrows(UserPrivilegeException.class,
                () -> underTest.signInBasicUser(username,password));

    }

    @Test
    void testSignInPrivilegedWithBasicAccountShouldThrowUserPrivilegeException() {
        // given
        String username = "username";
        String password = "password";
        User user = new User(username, password, Set.of(Role.USER) );
        Mockito.when(userRepository.findById(username)).thenReturn(Optional.of(user));

        // when
        // then
        assertThrows(UserPrivilegeException.class,
                () -> underTest.signInPrivilegedUser(username,password));

    }

    @Test
    void registerUser() {
    }
}