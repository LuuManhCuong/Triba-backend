package com.backend.triba.entities;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "[like]")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID likeId;

//    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "job_id")
    private Job job;

    // Các trường khác có thể thêm vào nếu cần
}
