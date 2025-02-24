package com.example.EventMangement.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Tickets")
public class Tickets{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;

    @ManyToOne
    @JoinColumn(name = "registrationId", nullable = false)
    private Registration registration;

    private String ticketType; // e.g., VIP, General, Early Bird

    private BigDecimal price;

    private LocalDateTime issuedDate = LocalDateTime.now();

    // Getters and Setters
    public Long getTicketId() {
        return ticketId;
    }
    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }
    public Registration getRegistration() {
        return registration;
    }
    public void setRegistration(Registration registration) {
        this.registration = registration;
    }
    public String getTicketType() {
        return ticketType;
    }
    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public LocalDateTime getIssuedDate() {
        return issuedDate;
    }
    public void setIssuedDate(LocalDateTime issuedDate) {
        this.issuedDate = issuedDate;
    }
}
