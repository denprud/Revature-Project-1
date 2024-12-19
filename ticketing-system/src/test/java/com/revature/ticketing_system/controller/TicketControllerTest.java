package com.revature.ticketing_system.controller;

import com.revature.controller.TicketController;
import com.revature.model.Ticket;
import com.revature.model.User;
import com.revature.service.TicketService;
import com.revature.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;

class TicketControllerTest {

    @Mock
    private TicketService ticketService;

    @Mock
    private UserService userService;

    @InjectMocks
    private TicketController ticketController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ticketController).build();
    }

    @Test
    void testSubmitTicket() throws Exception {
        User user = new User();
        user.setUsername("testuser");
        user.setRole("employee");

        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setDescription("Test Ticket");

        when(userService.findByUsername("testuser")).thenReturn(user);
        when(ticketService.submitTicket(any(Ticket.class))).thenReturn(ticket);

        mockMvc.perform(post("/api/tickets/submit")
                .param("username", "testuser")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"description\": \"Test Ticket\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value("Test Ticket"));

        verify(userService, times(1)).findByUsername("testuser");
        verify(ticketService, times(1)).submitTicket(any(Ticket.class));
    }

    @Test
    void testSubmitTicketForbidden() throws Exception {
        User user = new User();
        user.setUsername("testuser");
        user.setRole("manager");

        when(userService.findByUsername("testuser")).thenReturn(user);

        mockMvc.perform(post("/api/tickets/submit")
                .param("username", "testuser")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"description\": \"Test Ticket\"}"))
                .andExpect(status().isForbidden());

        verify(userService, times(1)).findByUsername("testuser");
        verify(ticketService, never()).submitTicket(any(Ticket.class));
    }

    @Test
    void testGetAllPendingTickets() throws Exception {
        User user = new User();
        user.setUsername("manager");
        user.setRole("manager");

        Ticket ticket1 = new Ticket();
        ticket1.setId(1L);
        ticket1.setDescription("Pending Ticket 1");

        Ticket ticket2 = new Ticket();
        ticket2.setId(2L);
        ticket2.setDescription("Pending Ticket 2");

        List<Ticket> tickets = Arrays.asList(ticket1, ticket2);

        when(userService.findByUsername("manager")).thenReturn(user);
        when(ticketService.getPendingTickets()).thenReturn(tickets);

        mockMvc.perform(get("/api/tickets/pending")
                .param("username", "manager"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].description").value("Pending Ticket 1"))
                .andExpect(jsonPath("$[1].description").value("Pending Ticket 2"));

        verify(userService, times(1)).findByUsername("manager");
        verify(ticketService, times(1)).getPendingTickets();
    }

    @Test
    void testProcessTicket() throws Exception {
        User user = new User();
        user.setUsername("manager");
        user.setRole("manager");

        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setDescription("Processed Ticket");

        when(userService.findByUsername("manager")).thenReturn(user);
        when(ticketService.processTicket(1L, "approved")).thenReturn(ticket);

        mockMvc.perform(post("/api/tickets/process")
                .param("ticketId", "1")
                .param("status", "approved")
                .param("username", "manager"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Processed Ticket"));

        verify(userService, times(1)).findByUsername("manager");
        verify(ticketService, times(1)).processTicket(1L, "approved");
    }

    @Test
    void testGetTicketsByUsername() throws Exception {
        Ticket ticket1 = new Ticket();
        ticket1.setId(1L);
        ticket1.setDescription("User Ticket 1");

        Ticket ticket2 = new Ticket();
        ticket2.setId(2L);
        ticket2.setDescription("User Ticket 2");

        List<Ticket> tickets = Arrays.asList(ticket1, ticket2);

        when(ticketService.getTicketsByUsername("testuser")).thenReturn(tickets);

        mockMvc.perform(get("/api/tickets/user")
                .param("username", "testuser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].description").value("User Ticket 1"))
                .andExpect(jsonPath("$[1].description").value("User Ticket 2"));

        verify(ticketService, times(1)).getTicketsByUsername("testuser");
    }
}