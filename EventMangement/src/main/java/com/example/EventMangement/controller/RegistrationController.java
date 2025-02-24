package com.example.EventMangement.controller;

import com.example.EventMangement.model.User;
import com.example.EventMangement.service.RegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registrations")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    // Existing endpoints...

    // New endpoint: Get all users registered for an event
    @GetMapping("/event/{eventId}/attendees")
    public ResponseEntity<List<User>> getAttendeesForEvent(@PathVariable Long eventId) {
        List<User> users = registrationService.getUsersRegisteredForEvent(eventId);
        return ResponseEntity.ok(users);
    }
}
