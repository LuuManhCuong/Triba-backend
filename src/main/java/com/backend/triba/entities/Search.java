package com.backend.triba.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Search {
	@Id
	@Column(unique = true, updatable = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID searchId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(columnDefinition = "NTEXT")
	private String keySearch;
	@Column(columnDefinition = "NTEXT")
	private String industries;
	@Column(columnDefinition = "NTEXT")
	private String positions;
	@Column(columnDefinition = "NTEXT")
	private String locations;
	@Column(columnDefinition = "NTEXT")
	private String workTypes;
	private LocalDate createAt;
}
