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
    public Events createEvent(Events event, Long organizerId, Long venueId) {
        User organizer = userRepository.findById(organizerId)
                .orElseThrow(() -> new RuntimeException("Organizer not found"));
        String role = organizer.getRole();
        if (role == null || (!role.equalsIgnoreCase("Organizer") && !role.equalsIgnoreCase("ADMIN"))) {
            throw new RuntimeException("Only organizers or admins can create events");
        }

        Venues venue = venueRepository.findById(venueId)
                .orElseThrow(() -> new RuntimeException("Venue not found"));
        event.setOrganiser(organizer);
        event.setVenue(venue);
        return eventRepository.save(event);
    }


    // Update event (Only the event organiser or an admin can update)
    public Events updateEvent(Long eventId, Events eventDetails, Long userId) {
        Events event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        // Compare using primitives; adjust as needed if userId is a Long.
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!event.getOrganiser().getUserId().equals(userId) &&
                !user.getRole().equalsIgnoreCase("ADMIN")) {
            throw new RuntimeException("You do not have permission to update this event");
        }

        event.setName(eventDetails.getName());
        event.setDescription(eventDetails.getDescription());
        event.setStartDateTime(eventDetails.getStartDateTime());
        event.setEndDateTime(eventDetails.getEndDateTime());
        event.setPrice(eventDetails.getPrice());
        return eventRepository.save(event);
    }

    // Delete event (Only Admins can delete an event)
    public void deleteEvent(Long eventId, Long userId) {
        Events event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.getRole().equalsIgnoreCase("ADMIN")) {
            throw new RuntimeException("Only admins can delete events");
        }
        eventRepository.delete(event);
    }
}
