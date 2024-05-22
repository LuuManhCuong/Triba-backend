package com.backend.triba.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import com.backend.triba.enums.ApplicationStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "job_application")
public class JobApplication {
	@Id
	@Column(unique = true, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "job_id")
    private Job job;

    @Column(name = "application_time")
    private LocalDateTime applicationTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ApplicationStatus status;
    
    // Constructors, getters, setters
}
