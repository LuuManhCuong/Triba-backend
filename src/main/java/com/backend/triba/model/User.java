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

@Entity
@Data
@Table(name = "[user]")
public class User {
	@Id
	@Column( unique = true, updatable = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID userId;
	@Column(columnDefinition = "nvarchar(50)")
	private String firstName;
	@Column(columnDefinition = "nvarchar(50)")
	private String lastName;
	private String email;
	private String gender;
	@Column(columnDefinition = "ntext")
	private String education;
	@Column(columnDefinition = "ntext")
	private String experience;
	@Column(columnDefinition = "ntext")
	private String skill;
	private String avatar;
	private String phoneNumber;
	private long point;
	private String coverImg;
	@Column(columnDefinition = "ntext")
	private String sologan;
	@Column(columnDefinition = "nvarchar(255)")
	private String address;
	private String scale;
	@Column(columnDefinition = "nvarchar(255)")
	private String industry;
	private String website;
	private int taxCode;
	private LocalDate createAt;
	private LocalDate updatateAt;
}
