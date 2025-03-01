package com.example.EventMangement.controller;

import com.example.EventMangement.model.Events;
import com.example.EventMangement.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // GET /api/events - Get all events (Authenticated users only)
    @GetMapping
    @PreAuthorize("isAuthenticated()") // Requires authentication
    public ResponseEntity<List<Events>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    // GET /api/events/{eventId} - Get event details (Authenticated users only)
    @GetMapping("/{eventId}")
    @PreAuthorize("isAuthenticated()") // Requires authentication
    public ResponseEntity<Events> getEvent(@PathVariable Long eventId) {
        return eventService.getEventById(eventId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/events/create - Create a new event (Organizers & Admins only)
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ORGANIZER') or hasAuthority('ADMIN')")
    public ResponseEntity<Events> createEvent(@RequestBody Events event) {
        return ResponseEntity.ok(eventService.createEvent(event));
    }

    // PUT /api/events/update - Update an event (Only the event creator can update it)
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ORGANIZER') or hasAuthority('ADMIN')")
    public ResponseEntity<Events> updateEvent(@RequestBody Events eventDetails) {
        return ResponseEntity.ok(eventService.updateEvent(eventDetails));
    }

    // DELETE /api/events/{eventId} - Delete an event (Admins only)
    @DeleteMapping("/{eventId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long eventId) {
        eventService.deleteEvent(eventId);
        return ResponseEntity.ok().build();
    }
}
