package com.backend.triba.entities;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Search {
	@Id
	@Column( unique = true, updatable = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID searchId;
	private UUID userId;
	@Column(columnDefinition = "nvarchar(255)")
	private String content;
	private LocalDate createAt;
}
