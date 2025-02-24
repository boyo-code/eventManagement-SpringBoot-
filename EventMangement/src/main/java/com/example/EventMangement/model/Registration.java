package com.example.EventMangement.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "registrations")
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "registrationid", nullable = false)
    private Long registrationid;

    @ManyToOne
    @JoinColumn(name = "eventid", nullable = false)
    private Events event;

    @ManyToOne
    @JoinColumn(name = "attendeeid", nullable = false)
    private User attendee;
    @Column(name = "registrationdate")
    private LocalDateTime registrationDate = LocalDateTime.now();

    @Column(nullable = false)
    private String status = "Confirmed";  // Default value

    // Getters and Setters
    public Long getId() {
        return registrationid;
    }
    public void setId(Long id) {
        this.registrationid = id;
    }
    public Events getEvent() {
        return event;
    }
    public void setEvent(Events event) {
        this.event = event;
    }
    public User getAttendee() {
        return attendee;
    }
    public void setAttendee(User attendee) {
        this.attendee = attendee;
    }
    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }
    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
