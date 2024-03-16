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
@Table(name = "[group]")
public class Group {
	@Id
	@Column( unique = true, updatable = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID groupId;
	private UUID adminGroupId;
	private UUID memberId;
	@Column(columnDefinition = "nvarchar(255)")
	private String groupName;
	private String avatar;
	private String coverImg;
	
	private LocalDate createAt;

}
