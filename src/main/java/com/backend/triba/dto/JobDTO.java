// JobDTO.java
package com.backend.triba.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.backend.triba.entities.Image;
import com.backend.triba.enums.JobStatus;

import lombok.Data;

@Data
public class JobDTO {
    private UUID ownerId;
    private String title;
    private String description;
    private String thumbnail;
    private String companyName;
    private String logo;
    private String address;
    private Double salary;
    private Double budget;
    private int quantity;
    private String category;
    private LocalDate createAt;
    private LocalDate updateAt;
    private LocalDate deadline;
    private String hastag;
    private JobStatus status;
    private List<String> industries;
    private List<String> positions;
    private List<String> locations;
    private List<String> workTypes;
    private List<String> images;
}
