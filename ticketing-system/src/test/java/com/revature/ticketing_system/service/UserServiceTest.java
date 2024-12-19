package com.revature.ticketing_system.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revature.model.Ticket;
import com.revature.model.User;
import com.revature.repository.TicketRepository;
import com.revature.repository.UserRepository;
import com.revature.service.TicketService;
import com.revature.service.UserService;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEnsureRegister() {
        User user = new User();
        user.setUsername("testuser1");
        user.setPassword("password");

        when(userRepository.save(user)).thenReturn(user);

        User result = userService.ensureRegister(user);

        assertEquals("testuser1", result.getUsername());
        assertEquals("password", result.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testLoginUser() {
        // Register a user before testing login
        User user = new User();
        user.setUsername("testuser1");
        user.setPassword("password");

        when(userRepository.save(user)).thenReturn(user);
        userService.registerUser(user);

        // Mock the findByUsername method for login
        when(userRepository.findByUsername("testuser1")).thenReturn(user);

        User result = userService.loginUser(user);

        assertEquals("testuser1", result.getUsername());
        assertEquals("password", result.getPassword());
        verify(userRepository, times(1)).findByUsername("testuser1");
    }

    @Test
    void testLoginUserInvalidCredentials() {
        User user = new User();
        user.setUsername("testuser1");
        user.setPassword("password");

        when(userRepository.findByUsername("testuser1")).thenReturn(user);

        User invalidUser = new User();
        invalidUser.setUsername("testuser1");
        invalidUser.setPassword("wrongpassword");

        try {
            userService.loginUser(invalidUser);
        } catch (Exception e) {
            assertEquals("Invalid credentials", e.getMessage());
        }
    }

    @Test
    void testLoginUserUserNotFound() {
        User user = new User();
        user.setUsername("testuser1");
        user.setPassword("password");

        when(userRepository.findByUsername("testuser1")).thenReturn(null);

        try {
            userService.loginUser(user);
        } catch (Exception e) {
            assertEquals("User not found", e.getMessage());
        }
    }

    @Test
    void testRegisterUser() {
        User user = new User();
        user.setUsername("testuser1");
        user.setPassword("password");

        when(userRepository.save(user)).thenReturn(user);

        User result = userService.registerUser(user);

        assertEquals("testuser1", result.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testFindByUsername() {
        User user = new User();
        user.setUsername("testuser1");
        user.setPassword("password");

        when(userRepository.findByUsername("testuser1")).thenReturn(user);

        User result = userService.findByUsername("testuser1");

        assertEquals("testuser1", result.getUsername());
        verify(userRepository, times(1)).findByUsername("testuser1");
    }
}
