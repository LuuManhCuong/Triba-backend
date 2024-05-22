package com.backend.triba.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.triba.entities.Job;
import com.backend.triba.entities.JobApplication;
import com.backend.triba.entities.User;
import com.backend.triba.enums.ApplicationStatus;
import com.backend.triba.repository.JobApplicationRepository;
import com.backend.triba.repository.JobRepository;
import com.backend.triba.repository.UserRepository;

@Service
public class JobApplicationService {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    public boolean applyForJob(UUID userId, UUID jobId) {
    		System.out.println("check userId: " + userId   + " jobId:  " + jobId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("Job not found"));

        // Check if the user has already applied for this job
        if (jobApplicationRepository.existsByUserAndJob(user, job)) {
            return false;
        } else {
            // Create a new JobApplication and save it
            JobApplication jobApplication = new JobApplication();
            jobApplication.setUser(user);
            jobApplication.setJob(job);
            jobApplication.setApplicationTime(LocalDateTime.now());
            jobApplication.setStatus(ApplicationStatus.PENDING); // Default to PENDING

            jobApplicationRepository.save(jobApplication);
            return true;
        }
    }

    public void approveJobApplication(UUID applicationId) {
        JobApplication jobApplication = jobApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Job application not found"));

        // Update the application status to APPROVED
        jobApplication.setStatus(ApplicationStatus.APPROVED);
        jobApplicationRepository.save(jobApplication);
    }

    public void rejectJobApplication(UUID applicationId) {
        JobApplication jobApplication = jobApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Job application not found"));

        // Update the application status to REJECTED
        jobApplication.setStatus(ApplicationStatus.REJECTED);
        jobApplicationRepository.save(jobApplication);
    }

//    public List<JobApplication> getJobApplications(UUID jobId) {
//        return jobApplicationRepository.findByJob_Id(jobId);
//    }

    public List<JobApplication> getJobApplicationById(UUID userId) {
    	return jobApplicationRepository.findByUser_UserId(userId);
    }
}
