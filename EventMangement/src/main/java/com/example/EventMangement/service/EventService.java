package com.example.EventMangement.service;

import com.example.EventMangement.model.Events;
import com.example.EventMangement.model.User;
import com.example.EventMangement.model.Venues;
import com.example.EventMangement.model.Role;
import com.example.EventMangement.Repository.EventRepository;
import com.example.EventMangement.Repository.UserRepository;
import com.example.EventMangement.Repository.VenueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;
    private final UserRepository userRepository;
    
    @PersistenceContext
    private EntityManager entityManager;

    public EventService(EventRepository eventRepository, VenueRepository venueRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<Events> getAllEvents() {
        List<Events> events = eventRepository.findAll();
        // Initialize lazy collections
        events.forEach(event -> {
            if (event.getOrganiser() != null) {
                event.getOrganiser().getName();
            }
            if (event.getVenue() != null) {
                event.getVenue().getName();
            }
        });
        return events;
    }

    @Transactional(readOnly = true)
    public Optional<Events> getEvent(Long eventId) {
        return eventRepository.findById(eventId);
    }

    @Transactional
    public Events createEvent(Events event) {
        User organizer = userRepository.findById(event.getOrganiser().getUserId())
                .orElseThrow(() -> new RuntimeException("Organizer not found"));
        
        if (organizer.getRole() == null) {
            throw new RuntimeException("User role is not set");
        }
        
        if (organizer.getRole() != Role.ORGANIZER && organizer.getRole() != Role.ADMIN) {
            throw new RuntimeException("Only organizers or admins can create events");
        }

        // Check if venue already exists by location
        Venues venue = venueRepository.findByLocation(event.getVenue().getLocation())
                .orElseGet(() -> {
                    // If venue does not exist, create a new one
                    Venues newVenue = new Venues();
                    newVenue.setLocation(event.getVenue().getLocation());
                    newVenue.setName(event.getVenue().getName());
                    newVenue.setCapacity(event.getVenue().getCapacity());
                    newVenue.setDescription(event.getVenue().getDescription());
                    return venueRepository.save(newVenue);
                });
        event.setVenue(venue);
        return eventRepository.save(event);
    }

    @Transactional
    public Events updateEvent(Long eventId, Events event) {
        Events existingEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        User organizer = existingEvent.getOrganiser();

        // Ensure the user updating is the event's creator
        if (!organizer.getUserId().equals(event.getOrganiser().getUserId())) {
            throw new RuntimeException("You are not authorized to update this event.");
        }

        existingEvent.setName(event.getName());
        existingEvent.setDescription(event.getDescription());
        existingEvent.setStartDateTime(event.getStartDateTime());
        existingEvent.setEndDateTime(event.getEndDateTime());
        existingEvent.setPrice(event.getPrice());
        return eventRepository.save(existingEvent);
    }

    @Transactional
    public void deleteEvent(Long eventId) {
        Events event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        User user = userRepository.findById(event.getOrganiser().getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getRole() != Role.ADMIN) {
            throw new RuntimeException("Only admins can delete events");
        }
        eventRepository.delete(event);
    }
}