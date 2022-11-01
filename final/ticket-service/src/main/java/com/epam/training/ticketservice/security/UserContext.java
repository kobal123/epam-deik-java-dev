package com.epam.training.ticketservice.security;

import com.epam.training.ticketservice.user.model.Role;
import com.epam.training.ticketservice.user.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class UserContext {

    private static Optional<User> user = Optional.empty();

    public static Optional<User> getUser() {
        return user;
    }

    public static void setUser(Optional<User> user) {
        UserContext.user = user;
    }

    public static boolean userHasRole(Role role) {

        return user.isPresent() && user.get().hasRole(role);
    }

    public static boolean isUserLoggedIn() {
        return user.isPresent();
    }


}
