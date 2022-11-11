package com.epam.training.ticketservice.user;

import com.epam.training.ticketservice.user.model.Role;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.Set;

@Value
public class UserDTO {
    String name;
    Set<Role> roles;

    public boolean hasRole(Role role) {
        return roles.contains(role);
    }
}
