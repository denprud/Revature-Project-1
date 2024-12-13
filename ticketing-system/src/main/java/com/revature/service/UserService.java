package com.revature.service;

import com.revature.exception.*;
import com.revature.model.User;
import com.revature.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User loginUser(User user) {
        User foundUser = findByUsername(user.getUsername());
        if (foundUser == null) {
            throw new UserNotFoundException("User not found");
        }
        if (!user.getPassword().equals(foundUser.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }
        return foundUser;
    }

    public User ensureRegister(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new InvalidInputException("Username or password cannot be empty");
        }
        User currentUser = findByUsername(username);
        if (currentUser != null) {
            throw new UserAlreadyExistsException("User already exists");
        }
        return registerUser(user);
    }

    private User findByUsername(String username) {
        // Implementation to find user by username
        return userRepository.findByUsername(username);
    }

    private User registerUser(User user) {
        // Implementation to register a new user
        return userRepository.save(user);
    }
}