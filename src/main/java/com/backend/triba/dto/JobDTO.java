// JobDTO.java
package com.backend.triba.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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
    private List<Long> industryIds;
    private List<Long> positionIds;
    private List<Long> locationIds;
    private List<Long> workTypeIds;
}
