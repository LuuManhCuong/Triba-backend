package com.backend.triba.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.triba.entities.JobApplication;
import com.backend.triba.enums.ApplicationStatus;
import com.backend.triba.service.JobApplicationService;
import com.backend.triba.service.JobService;

@RestController
@RequestMapping("/api/v1/user/job")
public class JobApplicationController {

    @Autowired
    private JobApplicationService jobApplicationService;
    
    @Autowired
    private JobService jobService;

    @PostMapping("/apply")
    public ResponseEntity<String> applyForJob(@RequestParam UUID jobId, @RequestParam UUID userId) {
        boolean success = jobApplicationService.applyForJob(userId, jobId);
        if (success) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Applied for job successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("You have already applied for this job.");
        }
    }
    
    @GetMapping("/applications/apply/{jobId}")
    public ResponseEntity<List<JobApplication>> getJobApplications(@PathVariable UUID jobId) {
        List<JobApplication> jobApplications = jobService.getJobApplicationsByJobId(jobId);
        return ResponseEntity.ok(jobApplications);
    }

    @GetMapping("/applications/{userId}")
    public ResponseEntity<List<JobApplication>> getJobApplicationByUserId(@PathVariable UUID userId) {
        List<JobApplication> application = jobApplicationService.getJobApplicationById(userId);
        return new ResponseEntity<>(application, HttpStatus.OK);
    }
    
    
    @PatchMapping("/applications/{id}/update-status/{status}")
    public ResponseEntity<String> updateJobApplicationStatus(@PathVariable UUID id, @PathVariable ApplicationStatus status) {
    	System.out.println("update status: " + id  + status);
        boolean success = jobApplicationService.updateJobApplicationStatus(id, status);
        if (success) {
            return ResponseEntity.ok("Job application status updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Job application not found.");
        }
    }
    
}

