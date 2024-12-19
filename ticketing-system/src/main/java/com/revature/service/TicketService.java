package com.revature.service;

import com.revature.model.Ticket;
import com.revature.repository.TicketRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Transactional
    public Ticket submitTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Transactional
    public List<Ticket> getPendingTickets() {
        return ticketRepository.findByStatus("Pending");
    }

    @Transactional
    public List<Ticket> getTicketsByUsernameAndStatus(String username, String status) {
        return ticketRepository.findByUserUsernameAndStatus(username, status);
    }

    @Transactional
    public List<Ticket> getTicketsByUsername(String username) {
        return ticketRepository.findByUserUsername(username);
    }

    @Transactional
    public Ticket processTicket(Long ticketId, String status) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));
        if (ticket.getStatus().equals("Pending")){
            ticket.setStatus(status);
            return ticketRepository.save(ticket);
        }
        return null;
    }
}