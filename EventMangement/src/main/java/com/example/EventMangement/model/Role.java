package com.example.EventMangement.model;

import jakarta.persistence.Converter;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Enumerated;

public enum Role {
    ADMIN,
    ORGANIZER,
    ATTENDEE;

    @Converter
    public static class RoleConverter implements AttributeConverter<Role, String> {
        @Override
        public String convertToDatabaseColumn(Role role) {
            return role == null ? null : role.name();
        }

        @Override
        public Role convertToEntityAttribute(String dbData) {
            if (dbData == null) {
                return null;
            }
            try {
                // Try exact match first
                return Role.valueOf(dbData);
            } catch (IllegalArgumentException e) {
                try {
                    // Try case-insensitive match
                    return Role.valueOf(dbData.toUpperCase());
                } catch (IllegalArgumentException ex) {
                    // Try matching with first letter capitalized
                    String normalized = dbData.substring(0, 1).toUpperCase() + dbData.substring(1).toLowerCase();
                    return Role.valueOf(normalized.toUpperCase());
                }
            }
        }
    }
} 