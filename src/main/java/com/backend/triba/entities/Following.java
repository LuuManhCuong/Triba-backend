package com.backend.triba.entities;

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
public class Following {
	@Id
	@Column( unique = true, updatable = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	private UUID userId;
	private UUID followingId;
	private LocalDate followAt;

}
