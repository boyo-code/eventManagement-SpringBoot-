package com.example.EventMangement.Repository;

import com.example.EventMangement.model.Venues;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venues,Long> {
}
