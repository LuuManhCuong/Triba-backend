package com.backend.triba.entities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class JobCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "job_category_id")
	private UUID jobCategoryId;

	@ManyToOne()
	private Job job;

	@ManyToOne()
	private Category category;
}
