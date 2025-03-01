package com.example.EventMangement.Repository;

import com.example.EventMangement.model.Venues;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VenueRepository extends JpaRepository<Venues,Long> {
    Optional<Venues> findByLocation(String location);
}
