package com.example.EventMangement.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Venues")
public class Venues {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "venueid")  // ✅ Fix the column name
    private Long venueId;

    private String name;
    private String location;
    private int capacity;
    private String description;

    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL)
    private List<Events> events;

    // Getters and Setters
    public Long getVenueId() {
        return venueId;
    }
    public void setVenueId(Long venueId) {
        this.venueId = venueId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public List<Events> getEvents() {
        return events;
    }
    public void setEvents(List<Events> events) {
        this.events = events;
    }
}
