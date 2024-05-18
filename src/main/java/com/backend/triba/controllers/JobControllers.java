package com.backend.triba.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.triba.dto.JobDTO;
import com.backend.triba.entities.Job;
import com.backend.triba.repository.JobRepository;
import com.backend.triba.service.JobService;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/user/job")
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
	    public Job createJob(@RequestBody JobDTO jobDTO) {
	        return jobService.saveJobWithDetails(jobDTO);
	    }
	
	 @GetMapping("/user/{userId}")
	    public List<Job> getJobsByUser(@PathVariable UUID userId) {
	        return jobService.getJobsByUser(userId);
	    }

	    @GetMapping("/industry")
	    public List<Job> getJobsByIndustry(@RequestParam String industryName) {
	    	System.out.println("filter industry: "+ industryName);
	        return jobService.getJobsByIndustry(industryName);
	    }

	    @GetMapping("/position")
	    public List<Job> getJobsByPosition(@RequestParam String positionName) {
	        return jobService.getJobsByPosition(positionName);
	    }

	    @GetMapping("/location")
	    public List<Job> getJobsByLocation(@RequestParam String locationName) {
	        return jobService.getJobsByLocation(locationName);
	    }

	    @GetMapping("/worktype")
	    public List<Job> getJobsByWorkType(@RequestParam String workTypeName) {
	        return jobService.getJobsByWorkType(workTypeName);
	    }

	    @GetMapping("/filter")
	    public List<Job> getJobsByMultipleCategories(
	            @RequestParam(required = false) String industryName,
	            @RequestParam(required = false) String positionName,
	            @RequestParam(required = false) String locationName,
	            @RequestParam(required = false) String workTypeName) {
	        return jobService.getJobsByMultipleCategories(industryName, positionName, locationName, workTypeName);
	    }
}
