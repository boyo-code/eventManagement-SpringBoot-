package com.example.EventMangement.Repository;

import com.example.EventMangement.model.Registration;
import com.example.EventMangement.model.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    // Get all registrations for a given event
    List<Registration> findByEvent(Events event);
    
    // Get all registrations for a given event ID
    List<Registration> findByEvent_EventId(Long eventId);
}
