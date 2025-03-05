package com.example.EventMangement.controller;

import com.example.EventMangement.model.Registration;
import com.example.EventMangement.model.User;
import com.example.EventMangement.service.RegistrationService;
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
@RequestMapping("/api/registrations")
@Tag(name = "Registration Management", description = "APIs for managing event registrations")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @Operation(summary = "Register for an event", description = "Register a user for a specific event")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully registered for the event"),
        @ApiResponse(responseCode = "400", description = "Invalid registration data"),
        @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @PostMapping("/register")
    public ResponseEntity<Registration> registerForEvent(@RequestBody Registration registration) {
        return ResponseEntity.ok(registrationService.registerForEvent(registration));
    }

    @Operation(summary = "Get all registrations", description = "Retrieve all event registrations (Admin only)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved all registrations"),
        @ApiResponse(responseCode = "403", description = "Access denied - Admin role required")
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Registration>> getAllRegistrations() {
        return ResponseEntity.ok(registrationService.getAllRegistrations());
    }

    @Operation(summary = "Get registration by ID", description = "Retrieve a specific registration by its ID (Admin only)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the registration"),
        @ApiResponse(responseCode = "404", description = "Registration not found"),
        @ApiResponse(responseCode = "403", description = "Access denied - Admin role required")
    })
    @GetMapping("/{registrationId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Registration> getRegistration(
            @Parameter(description = "ID of the registration to retrieve") 
            @PathVariable Long registrationId) {
        return registrationService.getRegistration(registrationId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get event attendees", description = "Retrieve all users registered for a specific event (Admin/Organizer only)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved event attendees"),
        @ApiResponse(responseCode = "404", description = "Event not found"),
        @ApiResponse(responseCode = "403", description = "Access denied - Admin/Organizer role required")
    })
    @GetMapping("/event/{eventId}/attendees")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ORGANIZER')")
    public ResponseEntity<List<User>> getAttendeesForEvent(
            @Parameter(description = "ID of the event to get attendees for") 
            @PathVariable Long eventId) {
        List<User> users = registrationService.getUsersRegisteredForEvent(eventId);
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Cancel registration", description = "Cancel a specific event registration")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully cancelled the registration"),
        @ApiResponse(responseCode = "404", description = "Registration not found")
    })
    @DeleteMapping("/{registrationId}")
    public ResponseEntity<Void> cancelRegistration(
            @Parameter(description = "ID of the registration to cancel") 
            @PathVariable Long registrationId) {
        registrationService.cancelRegistration(registrationId);
        return ResponseEntity.ok().build();
    }
}