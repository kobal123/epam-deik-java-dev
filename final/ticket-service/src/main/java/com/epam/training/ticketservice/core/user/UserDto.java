package com.epam.training.ticketservice.core.user;

import com.epam.training.ticketservice.core.user.model.Role;
import lombok.Value;

import java.util.Set;

@Value
public class UserDto {
    String name;
    Set<Role> roles;
}
