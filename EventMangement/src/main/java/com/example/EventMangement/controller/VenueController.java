package com.example.EventMangement.controller;

import com.example.EventMangement.model.Venues;
import com.example.EventMangement.service.VenueService;
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
@RequestMapping("/api/venues")
@Tag(name = "Venue Management", description = "APIs for managing venues")
public class VenueController {

    private final VenueService venueService;

    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @Operation(summary = "Create a new venue", description = "Create a new venue (Admin only)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venue created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid venue data"),
        @ApiResponse(responseCode = "403", description = "Access denied - Admin role required")
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Venues> createVenue(@RequestBody Venues venue) {
        return ResponseEntity.ok(venueService.createVenue(venue));
    }

    @Operation(summary = "Get all venues", description = "Retrieve all venues")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved all venues")
    })
    @GetMapping
    public ResponseEntity<List<Venues>> getAllVenues() {
        return ResponseEntity.ok(venueService.getAllVenues());
    }

    @Operation(summary = "Get venue by ID", description = "Retrieve a specific venue by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the venue"),
        @ApiResponse(responseCode = "404", description = "Venue not found")
    })
    @GetMapping("/{venueId}")
    public ResponseEntity<Venues> getVenue(
            @Parameter(description = "ID of the venue to retrieve") 
            @PathVariable Long venueId) {
        return venueService.getVenue(venueId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update venue", description = "Update an existing venue (Admin only)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venue updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid venue data"),
        @ApiResponse(responseCode = "403", description = "Access denied - Admin role required"),
        @ApiResponse(responseCode = "404", description = "Venue not found")
    })
    @PutMapping("/{venueId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Venues> updateVenue(
            @Parameter(description = "ID of the venue to update") 
            @PathVariable Long venueId,
            @RequestBody Venues venue) {
        return ResponseEntity.ok(venueService.updateVenue(venueId, venue));
    }

    @Operation(summary = "Delete venue", description = "Delete a venue (Admin only)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venue deleted successfully"),
        @ApiResponse(responseCode = "403", description = "Access denied - Admin role required"),
        @ApiResponse(responseCode = "404", description = "Venue not found")
    })
    @DeleteMapping("/{venueId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteVenue(
            @Parameter(description = "ID of the venue to delete") 
            @PathVariable Long venueId) {
        venueService.deleteVenue(venueId);
        return ResponseEntity.ok().build();
    }
} 