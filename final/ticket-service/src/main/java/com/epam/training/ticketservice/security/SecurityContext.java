package com.epam.training.ticketservice.security;

import com.epam.training.ticketservice.user.Role;
import com.epam.training.ticketservice.user.User;

import java.util.Optional;

public enum SecurityContext {
    USER;

    private Optional<User> user = Optional.empty();

    public Optional<User> getUser() {
        return user;
    }

    public void setUser(Optional<User> user) {
        this.user = user;
    }

    public boolean currentUserHasRole(Role role) {
        return user.isPresent() && user.get().getRole().contains(role);
    }

    public boolean isUserLoggedIn() {
        return user.isPresent();
    }
}
