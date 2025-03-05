package com.example.EventMangement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "events")
@Data
@Schema(description = "Represents an event in the system")
public class Events {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eventid")
    @Schema(description = "Unique identifier of the event")
    private Long eventId;

    @NotBlank(message = "Event name is required")
    @Size(max = 150)
    @Column(name = "name")
    @Schema(description = "Name of the event")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    @Schema(description = "Detailed description of the event")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizerid", nullable = false)
    @NotNull(message = "Organizer is required")
    @Schema(description = "User who organized the event")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User organiser;

    @NotNull(message = "Start date and time is required")
    @Column(name = "startdatetime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Start date and time of the event")
    private LocalDateTime startDateTime;

    @NotNull(message = "End date and time is required")
    @Column(name = "enddatetime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "End date and time of the event")
    private LocalDateTime endDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venueid", nullable = false)
    @NotNull(message = "Venue is required")
    @Schema(description = "Venue where the event will take place")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Venues venue;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    @Column(name = "price", columnDefinition = "decimal")
    @Schema(description = "Price for registration to this event", example = "99.99")
    private BigDecimal price;

    @JsonIgnore
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    @Schema(description = "List of registrations for this event")
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
