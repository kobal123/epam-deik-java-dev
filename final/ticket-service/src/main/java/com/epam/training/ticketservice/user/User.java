package com.epam.training.ticketservice.user;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user_table")
public class User {
    @Id
    private String name;
    private String password;

    @ElementCollection(targetClass = Role.class)
    @CollectionTable
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public User(String name,String password, Set<Role> roles) {
        this.name = name;
        this.password=password;
        this.roles = roles;
    }

    public User() {

    }

    public String getName() {
        return name;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public String getPassword() {
        return password;
    }
}
