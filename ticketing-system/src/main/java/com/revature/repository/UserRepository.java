package com.revature.repository;

import com.revature.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Finds a user by their username.
     *
     * @param username the username of the user to find
     * @return an Optional containing the found user, or an empty Optional if no user was found
     */
    User findByUsername(String username);
}