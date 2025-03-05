package com.example.EventMangement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@Schema(description = "Represents a user in the system")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    @Schema(description = "Unique identifier of the user")
    private Long userId;

    @NotBlank(message = "Name is required")
    @Size(max = 100)
    @Column(name = "name")
    @Schema(description = "Name of the user")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 100)
    @Column(name = "email", unique = true)
    @Schema(description = "Email address of the user")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(max = 255)
    @Column(name = "password")
    @Schema(description = "Password of the user (hashed)")
    private String password;

    @Convert(converter = Role.RoleConverter.class)
    @Column(name = "role", columnDefinition = "varchar(20)")
    @Schema(description = "Role of the user (ADMIN, ORGANIZER, ATTENDEE)")
    private Role role;

    @JsonIgnore
    @OneToMany(mappedBy = "organiser", cascade = CascadeType.ALL)
    @Schema(description = "List of events organized by this user")
    private List<Events> organizedEvents;

    @JsonIgnore
    @OneToMany(mappedBy = "attendee", cascade = CascadeType.ALL)
    @Schema(description = "List of event registrations for this user")
    private List<Registration> registrations;
}
