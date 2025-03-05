package com.example.EventMangement.controller;

import com.example.EventMangement.model.Events;
import com.example.EventMangement.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@Tag(name = "Event Management", description = "APIs for managing events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @Operation(summary = "Create a new event", description = "Create a new event (Organizer only)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Event created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid event data"),
        @ApiResponse(responseCode = "403", description = "Access denied - Organizer role required")
    })
    @PostMapping
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<Events> createEvent(@RequestBody Events event) {
        return ResponseEntity.ok(eventService.createEvent(event));
    }

    @Operation(summary = "Get all events", description = "Retrieve all events")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved all events")
    })
    @GetMapping
    public ResponseEntity<List<Events>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @Operation(summary = "Get event by ID", description = "Retrieve a specific event by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the event"),
        @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @GetMapping("/{eventId}")
    public ResponseEntity<Events> getEvent(
            @Parameter(description = "ID of the event to retrieve") 
            @PathVariable Long eventId) {
        return eventService.getEvent(eventId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update event", description = "Update an existing event (Organizer of the event only)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Event updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid event data"),
        @ApiResponse(responseCode = "403", description = "Access denied - Not the event organizer"),
        @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @PutMapping("/{eventId}")
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<Events> updateEvent(
            @Parameter(description = "ID of the event to update") 
            @PathVariable Long eventId,
            @RequestBody Events event) {
        return ResponseEntity.ok(eventService.updateEvent(eventId, event));
    }

    @Operation(summary = "Delete event", description = "Delete an event (Admin only)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Event deleted successfully"),
        @ApiResponse(responseCode = "403", description = "Access denied - Admin role required"),
        @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @DeleteMapping("/{eventId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEvent(
            @Parameter(description = "ID of the event to delete") 
            @PathVariable Long eventId) {
        eventService.deleteEvent(eventId);
        return ResponseEntity.ok().build();
    }
}