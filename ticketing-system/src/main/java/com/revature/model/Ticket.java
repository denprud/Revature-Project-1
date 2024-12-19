package com.revature.model;

import java.util.Objects;

import jakarta.persistence.*;

@Entity
@Table(name = "tickets")
public class Ticket {

    public Ticket() {
        this.status =  "Pending";
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String status;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    // @Version
    // private Long version;

    // Getters and setters
    public Long getId() {
        return id;
    }
    public Double getAmount() {
        return amount;
    }
    public String getDescription() {
        return description;
    }
    public String getStatus() {
        return status;
    }
    
    public User getUser() {
        return user;
    }
   
    public void setId(Long id) {
        this.id = id;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUser(User user) {
        this.user = user;
    }

}