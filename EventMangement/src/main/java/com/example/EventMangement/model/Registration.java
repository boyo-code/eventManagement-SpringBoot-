package com.example.EventMangement.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "registrations")
@Data
@Schema(description = "Represents an event registration")
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "registrationid")
    @Schema(description = "Unique identifier of the registration")
    private Long registrationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eventid", nullable = false)
    @NotNull(message = "Event is required")
    @Schema(description = "The event this registration is for")
    private Events event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attendeeid", nullable = false)
    @NotNull(message = "Attendee is required")
    @Schema(description = "The user who registered for the event")
    private User attendee;

    @NotNull(message = "Registration date is required")
    @Column(name = "registrationdate")
    @Schema(description = "Date and time when the registration was created")
    private LocalDateTime registrationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Schema(description = "Current status of the registration")
    private RegistrationStatus status;

    // Getters and Setters
    public Long getId() {
        return registrationId;
    }
    public void setId(Long id) {
        this.registrationId = id;
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
    public RegistrationStatus getStatus() {
        return status;
    }
    public void setStatus(RegistrationStatus status) {
        this.status = status;
    }
}
