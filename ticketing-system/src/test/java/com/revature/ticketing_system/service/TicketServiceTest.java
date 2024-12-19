package com.revature.ticketing_system.service;

import com.revature.model.Ticket;
import com.revature.model.User;
import com.revature.repository.TicketRepository;
import com.revature.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTickets() {
        Ticket ticket1 = new Ticket();
        ticket1.setAmount(20.00);
        ticket1.setDescription("Ticket 1");
        

        Ticket ticket2 = new Ticket();
        ticket1.setAmount(20.00);
        ticket2.setDescription("Ticket 2");
        

        List<Ticket> tickets = Arrays.asList(ticket1, ticket2);

        when(ticketRepository.findByStatus("Pending")).thenReturn(tickets);

        List<Ticket> result = ticketService.getPendingTickets();

        assertEquals(2, result.size());
        verify(ticketRepository, times(1)).findByStatus("Pending");
    }

    @Test
    void testCreateTicket() {
        Ticket ticket = new Ticket();
        ticket.setAmount(20.00);
        ticket.setDescription("New Ticket");

        when(ticketRepository.save(ticket)).thenReturn(ticket);

        Ticket result = ticketService.submitTicket(ticket);

        assertEquals("New Ticket", result.getDescription());
        verify(ticketRepository, times(1)).save(ticket);
    }

    @Test
    void testGetTicketsByUsernameAndStatus() {
        Ticket ticket1 = new Ticket();
        ticket1.setAmount(20.00);
        ticket1.setDescription("Ticket 1");
        

        Ticket ticket2 = new Ticket();
        ticket1.setAmount(20.00);
        ticket2.setDescription("Ticket 2");
        

        List<Ticket> tickets = Arrays.asList(ticket1, ticket2);

        User user = new User();
        user.setUsername("user");
        user.setPassword("user");

        when(ticketRepository.findByUserUsernameAndStatus(user.getUsername(), "Pending")).thenReturn(tickets);

        List<Ticket> result = ticketService.getTicketsByUsernameAndStatus(user.getUsername(), "Pending");

        assertEquals(2, result.size());
        verify(ticketRepository, times(1)).findByUserUsernameAndStatus(user.getUsername(), "Pending");
    }

    @Test
    void testGetTicketsByUsername() {
        Ticket ticket1 = new Ticket();
        ticket1.setAmount(20.00);
        ticket1.setDescription("Ticket 1");
        

        Ticket ticket2 = new Ticket();
        ticket1.setAmount(20.00);
        ticket2.setDescription("Ticket 2");
        

        List<Ticket> tickets = Arrays.asList(ticket1, ticket2);

        User user = new User();
        user.setUsername("user");
        user.setPassword("user");

        when(ticketRepository.findByUserUsername(user.getUsername())).thenReturn(tickets);

        List<Ticket> result = ticketService.getTicketsByUsername(user.getUsername());

        assertEquals(2, result.size());
        verify(ticketRepository, times(1)).findByUserUsername(user.getUsername());
    }

    @Test
    void testProcessTicket() {
        Ticket ticket = new Ticket();
        ticket.setAmount(20.00);
        ticket.setDescription("Ticket 1");
        ticket.setStatus("Pending");

        when(ticketRepository.findById(1L)).thenReturn(java.util.Optional.of(ticket));
        when(ticketRepository.save(ticket)).thenReturn(ticket);

        Ticket result = ticketService.processTicket(1L, "Approved");

        assertEquals("Approved", result.getStatus());
        verify(ticketRepository, times(1)).findById(1L);
        verify(ticketRepository, times(1)).save(ticket);
    }

}