package com.backend.triba.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "job")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID jobId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Comment> comments;
    
    @JsonIgnore
    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<JobApplication> jobApplications;

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
    private LocalDate createAt = LocalDate.now();
    private LocalDate updateAt;
    private LocalDate deadline;
    private String hastag;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "Job_Industry", joinColumns = @JoinColumn(name = "jobId"), inverseJoinColumns = @JoinColumn(name = "industryId"))
    private List<Industry> industries;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "Job_Position", joinColumns = @JoinColumn(name = "jobId"), inverseJoinColumns = @JoinColumn(name = "positionId"))
    private List<Position> positions;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "Job_Location", joinColumns = @JoinColumn(name = "jobId"), inverseJoinColumns = @JoinColumn(name = "locationId"))
    private List<Location> locations;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "Job_WorkType", joinColumns = @JoinColumn(name = "jobId"), inverseJoinColumns = @JoinColumn(name = "workTypeId"))
    private List<WorkType> workTypes;

    
    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Image> images;
    
    
    @Override
    public String toString() {
        return "Job [jobId=" + jobId + ", user=" + user + ", title=" + title + ", description=" + description
                + ", thumbnail=" + thumbnail + ", companyName=" + companyName + ", logo=" + logo + ", address="
                + address + ", salary=" + salary + ", budget=" + budget + ", quantity=" + quantity + ", category="
                + category + ", createAt=" + createAt + ", updateAt=" + updateAt + ", deadline=" + deadline
                + ", hastag=" + hastag + ", industries=" + industries + ", positions=" + positions + ", locations="
                + locations + ", workTypes=" + workTypes + "]";
    }
}
