package com.example.EventMangement.controller;

import com.example.EventMangement.model.Events;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.EventMangement.service.EventService;

import java.util.*;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // GET /api/events - Get all events
    @GetMapping
    public ResponseEntity<List<Events>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    // GET /api/events/{eventId} - Get event details
    @GetMapping("/{eventId}")
    public ResponseEntity<Events> getEvent(@PathVariable Long eventId) {
        return eventService.getEventById(eventId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/events/create - Create a new event (Organizers only)
    // Pass organizerId and venueId as request parameters.
    @PostMapping("/create")
    public ResponseEntity<Events> createEvent(@RequestBody Events event,
                                             @RequestParam Long organizerId,
                                             @RequestParam Long venueId) {
        return ResponseEntity.ok(eventService.createEvent(event, organizerId, venueId));
    }

    // PUT /api/events/{eventId} - Update an event (Organizers only)
    // Pass userId as a request parameter for permission checking.
    @PutMapping("/{eventId}")
    public ResponseEntity<Events> updateEvent(@PathVariable Long eventId,
                                             @RequestBody Events eventDetails,
                                             @RequestParam Long userId) {
        return ResponseEntity.ok(eventService.updateEvent(eventId, eventDetails, userId));
    }

    // DELETE /api/events/{eventId} - Delete an event (Admins only)
    // Pass userId as a request parameter for permission checking.
    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long eventId,
                                            @RequestParam Long userId) {
        eventService.deleteEvent(eventId, userId);
        return ResponseEntity.ok().build();
    }
}