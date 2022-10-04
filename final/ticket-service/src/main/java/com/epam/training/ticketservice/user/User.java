package com.epam.training.ticketservice.user;

import java.util.List;
import java.util.Set;


public class User {
    private final String name;
    private Set<Authority> authorities;

    public User(String name, Set<Authority> authorities) {
        this.name = name;
        this.authorities = authorities;
    }

    public String getName() {
        return name;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }
}
