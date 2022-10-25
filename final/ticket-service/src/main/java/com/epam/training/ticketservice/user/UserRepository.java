package com.epam.training.ticketservice.user;

import com.epam.training.ticketservice.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

}
