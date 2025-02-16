package model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
public class Tickets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketid;

    @OneToOne
    @JoinColumn(name = "registrationid", nullable = false)
    private Registration registration;

    private String tickettype;
    private BigDecimal price;
    private LocalDateTime issueddate;

    public Long getTicketid() {
        return ticketid;
    }

    public void setTicketid(Long ticketid) {
        this.ticketid = ticketid;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public String getTickettype() {
        return tickettype;
    }

    public void setTickettype(String tickettype) {
        this.tickettype = tickettype;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getIssueddate() {
        return issueddate;
    }

    public void setIssueddate(LocalDateTime issueddate) {
        this.issueddate = issueddate;
    }
}

