package com.backend.triba.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Comment {
	@Id
	@Column( unique = true, updatable = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID commentId;
	private UUID ownerId;
	private UUID userId;
	@Column(columnDefinition = "ntext")
	private String comment;
	private LocalDate createdAt;
	private LocalDate updatedAt;

}
