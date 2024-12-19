package com.revature.ticketing_system.controller;

import com.revature.controller.UserController;
import com.revature.exception.InvalidCredentialsException;
import com.revature.exception.InvalidInputException;
import com.revature.exception.UserAlreadyExistsException;
import com.revature.exception.UserNotFoundException;
import com.revature.model.User;
import com.revature.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testLoginUser_Success() throws Exception {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");

        when(userService.loginUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"testuser\", \"password\": \"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));

        verify(userService, times(1)).loginUser(any(User.class));
    }

    @Test
    void testLoginUser_Unauthorized() throws Exception {
        when(userService.loginUser(any(User.class))).thenThrow(new InvalidCredentialsException("Invalid Credentials"));

        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"testuser\", \"password\": \"wrongpassword\"}"))
                .andExpect(status().isUnauthorized());

        verify(userService, times(1)).loginUser(any(User.class));
    }

    @Test
    void testRegisterUser_Success() throws Exception {
        User user = new User();
        user.setUsername("newuser");
        user.setPassword("password");

        when(userService.ensureRegister(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"newuser\", \"password\": \"password\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("newuser"));

        verify(userService, times(1)).ensureRegister(any(User.class));
    }

    @Test
    void testRegisterUser_Conflict() throws Exception {
        when(userService.ensureRegister(any(User.class))).thenThrow(new UserAlreadyExistsException("User already exists"));

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"existinguser\", \"password\": \"password\"}"))
                .andExpect(status().isConflict());

        verify(userService, times(1)).ensureRegister(any(User.class));
    }

    @Test
    void testRegisterUser_BadRequest() throws Exception {
        when(userService.ensureRegister(any(User.class))).thenThrow(new InvalidInputException("Invalid input"));

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"\", \"password\": \"password\"}"))
                .andExpect(status().isBadRequest());

        verify(userService, times(1)).ensureRegister(any(User.class));
    }
}