package com.example.EventMangement.Repository;

import com.example.EventMangement.model.Venues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VenueRepository extends JpaRepository<Venues, Long> {
    Optional<Venues> findByLocation(String location);
}
