package com.example.EventMangement.service;

import com.example.EventMangement.model.Events;
import com.example.EventMangement.model.User;
import com.example.EventMangement.model.Venues;
import com.example.EventMangement.Repository.EventRepository;
import com.example.EventMangement.Repository.UserRepository;
import com.example.EventMangement.Repository.VenueRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;
    private final UserRepository userRepository;

    public EventService(EventRepository eventRepository, VenueRepository venueRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
        this.userRepository = userRepository;
    }

    public List<Events> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Events> getEventById(Long eventId) {
        return eventRepository.findById(eventId);
    }

    // Create a new event (Only Organizers & Admins)
    public Events createEvent(Events event) {
        User organizer = userRepository.findById(event.getOrganiser().getUserId())
                .orElseThrow(() -> new RuntimeException("Organizer not found"));
        String role = organizer.getRole();
        if (role == null || (!role.equalsIgnoreCase("Organizer") && !role.equalsIgnoreCase("ADMIN"))) {
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


    // Update event (Only the event organiser or an admin can update)
    public Events updateEvent(Events event) {
        Events existingEvent = eventRepository.findById(event.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found"));
        User organizer = existingEvent.getOrganiser();

        // Ensure the user updating is the event's creator
        if (!organizer.getUserId().equals(event.getOrganiser().getUserId())) {
            throw new RuntimeException("You are not authorized to update this event.");
        }

        event.setName(event.getName());
        event.setDescription(event.getDescription());
        event.setStartDateTime(event.getStartDateTime());
        event.setEndDateTime(event.getEndDateTime());
        event.setPrice(event.getPrice());
        return eventRepository.save(event);
    }

    // Delete event (Only Admins can delete an event)
    public void deleteEvent(Long eventId) {
        Events event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        User user = userRepository.findById(event.getOrganiser().getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.getRole().equalsIgnoreCase("ADMIN")) {
            throw new RuntimeException("Only admins can delete events");
        }
        eventRepository.delete(event);
    }
}
