package model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "venues")
public class Venues {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OneToMany
    private long venueid;
    private String name;
    private String location;
    private int capacity;
    private String description;
    @OneToMany(mappedBy = "venue",cascade = CascadeType.ALL)
    private List<Events>events;

    public long getVenueid() {
        return venueid;
    }

    public void setVenueid(long venueid) {
        this.venueid = venueid;
    }

    public List<Events> getEvents() {
        return events;
    }

    public void setEvents(List<Events> events) {
        this.events = events;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
