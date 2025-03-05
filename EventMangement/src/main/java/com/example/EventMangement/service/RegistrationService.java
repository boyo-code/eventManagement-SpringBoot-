package com.example.EventMangement.service;

import com.example.EventMangement.Repository.RegistrationRepository;
import com.example.EventMangement.model.Registration;
import com.example.EventMangement.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RegistrationService {
    private final RegistrationRepository registrationRepository;

    public RegistrationService(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    @Transactional
    public Registration registerForEvent(Registration registration) {
        return registrationRepository.save(registration);
    }

    @Transactional(readOnly = true)
    public List<Registration> getAllRegistrations() {
        return registrationRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Registration> getRegistration(Long registrationId) {
        return registrationRepository.findById(registrationId);
    }

    @Transactional(readOnly = true)
    public List<User> getUsersRegisteredForEvent(Long eventId) {
        return registrationRepository.findByEvent_EventId(eventId)
                .stream()
                .map(Registration::getAttendee)
                .toList();
    }

    @Transactional
    public void cancelRegistration(Long registrationId) {
        registrationRepository.deleteById(registrationId);
    }
}