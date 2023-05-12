package com.example.demo.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_tracking")
public class UserTracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "access_time")
    private LocalDateTime accessTime;

    public UserTracking(String username, String ipAddress, LocalDateTime accessTime) {
        this.username = username;
        this.ipAddress = ipAddress;
        this.accessTime = accessTime;
    }

    // getters, setters, constructors
}
