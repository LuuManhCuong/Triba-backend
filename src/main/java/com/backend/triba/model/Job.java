package com.backend.triba.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "[job]")
public class Job {
	@Id
	@Column( unique = true, updatable = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID jobId;
	private UUID ownerId;
	@Column(columnDefinition = "nvarchar(255)")
	private String title;
	@Column(columnDefinition = "ntext")
	private String description;
	private String thumbnail;
	@Column(columnDefinition = "nvarchar(255)")
	private String companyName;
	private String logo;
	@Column(columnDefinition = "nvarchar(255)")
	private String address;
	private Double salary;
	private Double budget;
	private int quantity;
	private String category;
	private LocalDate createAt;
	private LocalDate updateAt;
	private LocalDate deadline;
	private String hastag;
	
	
}
