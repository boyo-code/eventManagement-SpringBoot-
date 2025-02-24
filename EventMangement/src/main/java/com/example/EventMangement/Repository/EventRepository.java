package com.example.EventMangement.Repository;

import com.example.EventMangement.model.Events;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Events, Long> {
}

