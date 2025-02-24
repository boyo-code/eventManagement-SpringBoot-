package com.example.EventMangement.service;

import com.example.EventMangement.model.Registration;
import com.example.EventMangement.model.User;
import com.example.EventMangement.Repository.RegistrationRepository;
import com.example.EventMangement.Repository.EventRepository;
import com.example.EventMangement.model.Events;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegistrationService {
    private final RegistrationRepository registrationRepository;
    private final EventRepository eventRepository;

    public RegistrationService(RegistrationRepository registrationRepository, EventRepository eventRepository) {
        this.registrationRepository = registrationRepository;
        this.eventRepository = eventRepository;
    }

    // Existing methods...

    // New method: get users who registered for a given event
    public List<User> getUsersRegisteredForEvent(Long eventId) {
        Events event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        List<Registration> registrations = registrationRepository.findByEvent(event);

        // Extract the attendees (users) from the registrations
        return registrations.stream()
                .map(Registration::getAttendee)
                .collect(Collectors.toList());
    }
}
