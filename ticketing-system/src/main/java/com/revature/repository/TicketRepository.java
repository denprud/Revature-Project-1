package com.revature.repository;

import com.revature.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    /**
     * Finds a list of tickets by their status.
     *
     * @param status the status of the ticket to filter by
     * @return an List object containing the found tickets
     */
    List<Ticket> findByStatus(String status);

    List<Ticket> findByUserUsername(String username);

    List<Ticket> findByUserUsernameAndStatus(String username, String status);


}