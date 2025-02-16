package model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "registrations")
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long registrationid;

    @ManyToOne
    @JoinColumn(name = "eventid", nullable = false)
    private Events event;

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    private User attendee;

    private LocalDateTime registrationdate;
    private String status; // e.g., Confirmed, Pending

    @OneToOne(mappedBy = "registration", cascade = CascadeType.ALL)
    private Tickets ticket;

    public Long getRegistrationid() {
        return registrationid;
    }

    public void setRegistrationid(Long registrationid) {
        this.registrationid = registrationid;
    }

    public Tickets getTicket() {
        return ticket;
    }

    public void setTicket(Tickets ticket) {
        this.ticket = ticket;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getRegistrationdate() {
        return registrationdate;
    }

    public void setRegistrationdate(LocalDateTime registrationdate) {
        this.registrationdate = registrationdate;
    }

    public User getAttendee() {
        return attendee;
    }

    public void setAttendee(User attendee) {
        this.attendee = attendee;
    }

    public Events getEvent() {
        return event;
    }

    public void setEvent(Events event) {
        this.event = event;
    }
}

