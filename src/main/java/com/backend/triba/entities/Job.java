package com.backend.triba.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "[job]")
public class Job {
    @Id
    @Column(unique = true, updatable = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID jobId;

  
    
    @ManyToOne(cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

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

    @OneToMany(mappedBy = "job", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<JobCategory> jobCategories;

    @ManyToMany
    @JoinTable(
        name = "Job_Industry",
        joinColumns = @JoinColumn(name = "jobId"),
        inverseJoinColumns = @JoinColumn(name = "industryId")
    )
    private List<Industry> industries;

    @ManyToMany
    @JoinTable(
        name = "Job_Position",
        joinColumns = @JoinColumn(name = "jobId"),
        inverseJoinColumns = @JoinColumn(name = "positionId")
    )
    private List<Position> positions;

    @ManyToMany
    @JoinTable(
        name = "Job_Location",
        joinColumns = @JoinColumn(name = "jobId"),
        inverseJoinColumns = @JoinColumn(name = "locationId")
    )
    private List<Location> locations;

    @ManyToMany
    @JoinTable(
        name = "Job_WorkType",
        joinColumns = @JoinColumn(name = "jobId"),
        inverseJoinColumns = @JoinColumn(name = "workTypeId")
    )
    private List<WorkType> workTypes;

	@Override
	public String toString() {
		return "Job [jobId=" + jobId + ", title=" + title + ", description=" + description + ", thumbnail=" + thumbnail
				+ ", companyName=" + companyName + ", logo=" + logo + ", address=" + address + ", salary=" + salary
				+ ", budget=" + budget + ", quantity=" + quantity + ", category=" + category + ", createAt=" + createAt
				+ ", updateAt=" + updateAt + ", deadline=" + deadline + ", hastag=" + hastag + ", jobCategories="
				+ jobCategories + ", industries=" + industries + ", positions=" + positions + ", locations=" + locations
				+ ", workTypes=" + workTypes + "]";
	}
}
