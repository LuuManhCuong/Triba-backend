package com.backend.triba.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.triba.entities.Job;
import com.backend.triba.entities.JobApplication;
import com.backend.triba.entities.User;

public interface JobApplicationRepository extends JpaRepository<JobApplication, UUID> {

	boolean existsByUserAndJob(User user, Job job);
//	List<JobApplication> findByJob_Id(UUID jobId);
	List<JobApplication> findByUser_UserId(UUID userId);
}
