package com.backend.triba.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Hastag {
	@Id
	@Column( unique = true, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID hastagId;
	private UUID jobId;
	private String name;
}
