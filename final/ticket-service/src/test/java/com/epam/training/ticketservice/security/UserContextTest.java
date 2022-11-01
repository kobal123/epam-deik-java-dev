package com.epam.training.ticketservice.security;

import com.epam.training.ticketservice.user.model.Role;
import com.epam.training.ticketservice.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserContextTest {


    @BeforeEach
    void setup() {
        UserContext.setUser(Optional.empty());
    }

    @Test
    void TestUserHasRoleShouldReturnFalseWhenUserIsEmpty() {
        assertFalse(UserContext.userHasRole(Role.USER));
    }

    @Test
    void TestUserHasRoleShouldReturnFalseWhenUserDoesNotHaveRole() {
        // given
        User user = new User("name","password", Set.of(Role.USER));

        // when
        UserContext.setUser(Optional.of(user));

        // then
        assertFalse(UserContext.userHasRole(Role.ADMIN));
    }

    @Test
    void TestUserHasRoleShouldReturnTrueWhenUserHasRole() {
        // given
        User user = new User("name","password", Set.of(Role.USER));

        // when
        UserContext.setUser(Optional.of(user));

        // then
        assertTrue(UserContext.userHasRole(Role.USER));
    }
}