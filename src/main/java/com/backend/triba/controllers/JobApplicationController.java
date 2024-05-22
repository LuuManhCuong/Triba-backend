package com.backend.triba.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.triba.entities.JobApplication;
import com.backend.triba.service.JobApplicationService;

@RestController
@RequestMapping("/api/v1/user/job")
public class JobApplicationController {

    @Autowired
    private JobApplicationService jobApplicationService;

    @PostMapping("/apply")
    public ResponseEntity<String> applyForJob(@RequestParam UUID jobId, @RequestParam UUID userId) {
        boolean success = jobApplicationService.applyForJob(userId, jobId);
        if (success) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Applied for job successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("You have already applied for this job.");
        }
    }
//    @GetMapping("/{jobId}/applications")
//    public ResponseEntity<List<JobApplication>> getJobApplications(@PathVariable UUID jobId) {
//        List<JobApplication> applications = jobApplicationService.getJobApplications(jobId);
//        return new ResponseEntity<>(applications, HttpStatus.OK);
//    }

    @GetMapping("/applications/{userId}")
    public ResponseEntity<List<JobApplication>> getJobApplicationByUserId(@PathVariable UUID userId) {
        List<JobApplication> application = jobApplicationService.getJobApplicationById(userId);
        return new ResponseEntity<>(application, HttpStatus.OK);
    }
}

