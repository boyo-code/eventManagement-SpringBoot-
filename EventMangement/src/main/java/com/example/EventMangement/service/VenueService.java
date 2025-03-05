package com.example.EventMangement.service;

import com.example.EventMangement.Repository.VenueRepository;
import com.example.EventMangement.model.Venues;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class VenueService {
    private final VenueRepository venueRepository;

    public VenueService(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    @Transactional
    public Venues createVenue(Venues venue) {
        return venueRepository.save(venue);
    }

    @Transactional(readOnly = true)
    public List<Venues> getAllVenues() {
        return venueRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Venues> getVenue(Long venueId) {
        return venueRepository.findById(venueId);
    }

    @Transactional
    public Venues updateVenue(Long venueId, Venues venue) {
        Venues existingVenue = venueRepository.findById(venueId)
                .orElseThrow(() -> new RuntimeException("Venue not found"));
        venue.setVenueId(existingVenue.getVenueId());
        return venueRepository.save(venue);
    }

    @Transactional
    public void deleteVenue(Long venueId) {
        venueRepository.deleteById(venueId);
    }
} 