package com.backend.triba.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.triba.entities.Job;
import com.backend.triba.entities.Location;
import com.backend.triba.service.JobApplicationService;
import com.backend.triba.service.JobService;
import com.backend.triba.service.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

	@GetMapping
	public ResponseEntity<String> sayHello() {
		return ResponseEntity.ok("admin xin chào");
	}
	
	@Autowired
    private JobService jobService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JobApplicationService jobApplicationService;

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
    	System.out.println("admin statitics");
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalJobs", jobService.getTotalJobs());
        stats.put("totalUsers", userService.getTotalUsers());
        stats.put("totalApplications", jobApplicationService.getTotalApplications());
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/count-by-location")
    public ResponseEntity<Map<String, Integer>> countJobsByLocation() {
        List<Job> jobs = jobService.getAllJobs(); // Lấy danh sách công việc từ cơ sở dữ liệu

        Map<String, Integer> jobCountByLocation = new HashMap<>();

        // Đếm số lượng công việc theo địa điểm
        for (Job job : jobs) {
            List<Location> locations = job.getLocations(); // Lấy danh sách địa điểm của mỗi công việc
            for (Location location : locations) {
                String locationName = location.getName(); // Lấy tên địa điểm
                jobCountByLocation.put(locationName, jobCountByLocation.getOrDefault(locationName, 0) + 1);
            }
        }

        return ResponseEntity.ok(jobCountByLocation);
    }
}
