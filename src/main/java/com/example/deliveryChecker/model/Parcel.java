package com.example.deliveryChecker.model;

import jakarta.persistence.*;

@Table(name = "parcel")
@Entity
public class Parcel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String trackingCode;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String location;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Parcel() {}

    public Parcel(Long id, String trackingCode, String status, String location, User user) {
        this.id = id;
        this.trackingCode = trackingCode;
        this.status = status;
        this.location = location;
        this.user = user;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }

    public String getStatus() {
        return status;
    }

    public String getLocation() {
        return location;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
