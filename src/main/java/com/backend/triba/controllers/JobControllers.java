package com.backend.triba.controllers;

import java.util.List;

import javax.swing.Spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.triba.model.Job;
import com.backend.triba.repository.JobRepository;
import com.backend.triba.service.JobService;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/triba/job")
public class JobControllers {
	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private JobService jobService;
	
	@GetMapping("/hello")
	public ResponseEntity<String> sayhello() {
	    return ResponseEntity.ok("Hello world");
	}

	@GetMapping
	public ResponseEntity<List<Job>> getAllJobs() {
	    List<Job> jobs = jobRepository.findAll();
	    return ResponseEntity.ok(jobs);
	}
	
	@PostMapping("/add")
	public ResponseEntity<Job> createJob(@RequestBody Job job) {
		System.out.println("run here");
	    Job createdJob = jobService.createJob(job);
	    return ResponseEntity.ok(createdJob);
	}
}
