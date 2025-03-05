package com.example.EventMangement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;
import lombok.Data;

@Entity
@Table(name = "venues")
@Data
@Schema(description = "Represents a venue where events can be held")
public class Venues {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "venueid")
    @Schema(description = "Unique identifier of the venue")
    private Long venueId;

    @NotBlank(message = "Name is required")
    @Size(max = 100)
    @Column(name = "name")
    @Schema(description = "Name of the venue")
    private String name;

    @NotBlank(message = "Location is required")
    @Size(max = 255)
    @Column(name = "location")
    @Schema(description = "Location of the venue")
    private String location;

    @NotNull(message = "Capacity is required")
    @Positive(message = "Capacity must be positive")
    @Column(name = "capacity")
    @Schema(description = "Capacity of the venue")
    private Integer capacity;

    @Column(name = "description", columnDefinition = "TEXT")
    @Schema(description = "Description of the venue")
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL)
    @Schema(description = "List of events scheduled at this venue")
    private List<Events> events;

    public List<Events> getEvents() {
        return events;
    }

    public void setEvents(List<Events> events) {
        this.events = events;
    }

    public @NotNull(message = "Capacity is required") @Positive(message = "Capacity must be positive") Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(@NotNull(message = "Capacity is required") @Positive(message = "Capacity must be positive") Integer capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public @NotBlank(message = "Location is required") @Size(max = 255) String getLocation() {
        return location;
    }

    public void setLocation(@NotBlank(message = "Location is required") @Size(max = 255) String location) {
        this.location = location;
    }

    public @NotBlank(message = "Name is required") @Size(max = 100) String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name is required") @Size(max = 100) String name) {
        this.name = name;
    }

    public Long getVenueId() {
        return venueId;
    }

    public void setVenueId(Long venueId) {
        this.venueId = venueId;
    }
}
