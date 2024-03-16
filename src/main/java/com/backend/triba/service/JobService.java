package com.backend.triba.service;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.triba.model.Job;
import com.backend.triba.repository.JobRepository;

@Service
public class JobService {

	 @Autowired
	    private JobRepository jobRepository;

	    public Job createJob(Job job) {
	        job.setCreateAt(LocalDate.now());
	        job.setUpdateAt(LocalDate.now());
	        return jobRepository.save(job);
	    }
}
