package com.revature.controller;

import com.revature.model.Ticket;
import com.revature.model.User;
import com.revature.service.TicketService;
import com.revature.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    @PostMapping("/submit")
    public ResponseEntity<Ticket> submitTicket(@RequestBody Ticket ticket, @RequestParam String username) {
        try {
            User user = userService.findByUsername(username);
            if (user == null || !user.getRole().equals("employee")) {
                return ResponseEntity.status(403).build(); // Forbidden
            }
            ticket.setUser(user);
            Ticket submittedTicket = ticketService.submitTicket(ticket);
            return ResponseEntity.status(201).body(submittedTicket);
        } catch (ObjectOptimisticLockingFailureException e) {
            return ResponseEntity.status(409).build(); // Conflict
        }
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Ticket>> getAllPendingTickets(@RequestParam String username) {
        User user = userService.findByUsername(username);
        if (user == null || !user.getRole().equals("manager")) {
            return ResponseEntity.status(403).build(); // Forbidden
        }
        List<Ticket> pendingTickets = ticketService.getPendingTickets();
        return ResponseEntity.ok(pendingTickets);
    }

    @PostMapping("/process")
    public ResponseEntity<Ticket> processTicket(@RequestParam Long ticketId, @RequestParam String status, @RequestParam String username) {
        User user = userService.findByUsername(username);
        if (user == null || !user.getRole().equals("manager")) {
            return ResponseEntity.status(403).build(); // Forbidden
        }
        Ticket processedTicket = ticketService.processTicket(ticketId, status);
        if (processedTicket == null) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
        return ResponseEntity.ok(processedTicket);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Ticket>> getTicketsByUsername(@RequestParam String username) {
        List<Ticket> tickets = ticketService.getTicketsByUsername(username);
        return ResponseEntity.ok(tickets);
    }
}