package com.example.EventMangement.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Events")
public class Events {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eventid")
    private Long eventId;

    private String name;
    private String description;
    @Column(name = "startdatetime")
    private LocalDateTime startDateTime;
    @Column(name = "enddatetime")
    private LocalDateTime endDateTime;


    // Price for registration to this event
    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "organizerid", nullable = false)
    private User organiser;

    @ManyToOne
    @JoinColumn(name = "venueid", nullable = false)
    private Venues venue;

    // One event can have many registrations.
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Registration> registrations;

    // Getters and Setters
    public Long getEventId() {
        return eventId;
    }
    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public User getOrganiser() {
        return organiser;
    }
    public void setOrganiser(User organiser) {
        this.organiser = organiser;
    }
    public Venues getVenue() {
        return venue;
    }
    public void setVenue(Venues venue) {
        this.venue = venue;
    }
    public List<Registration> getRegistrations() {
        return registrations;
    }
    public void setRegistrations(List<Registration> registrations) {
        this.registrations = registrations;
    }
}
