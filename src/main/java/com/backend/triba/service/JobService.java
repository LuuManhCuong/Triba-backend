package com.backend.triba.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.triba.dto.JobDTO;
import com.backend.triba.entities.Industry;
import com.backend.triba.entities.Job;
import com.backend.triba.entities.Location;
import com.backend.triba.entities.Position;
import com.backend.triba.entities.User;
import com.backend.triba.entities.WorkType;
import com.backend.triba.repository.IndustryRepository;
import com.backend.triba.repository.JobRepository;
import com.backend.triba.repository.LocationRepository;
import com.backend.triba.repository.PositionRepository;
import com.backend.triba.repository.UserRepository;
import com.backend.triba.repository.WorkTypeRepository;

@Service
public class JobService {

	 @Autowired
	    private JobRepository jobRepository;
	 
	 @Autowired
	 private UserRepository userRepository;
	 
	 @Autowired
	    private IndustryRepository industryRepository;

	    @Autowired
	    private PositionRepository positionRepository;

	    @Autowired
	    private LocationRepository locationRepository;

	    @Autowired
	    private WorkTypeRepository workTypeRepository;

	 public Job saveJobWithDetails(JobDTO jobDTO) {
	        Job job = new Job();

	        // Set basic job details
	        job.setTitle(jobDTO.getTitle());
	        job.setDescription(jobDTO.getDescription());
	        job.setThumbnail(jobDTO.getThumbnail());
	        job.setCompanyName(jobDTO.getCompanyName());
	        job.setLogo(jobDTO.getLogo());
	        job.setAddress(jobDTO.getAddress());
	        job.setSalary(jobDTO.getSalary());
	        job.setBudget(jobDTO.getBudget());
	        job.setQuantity(jobDTO.getQuantity());
	        job.setCategory(jobDTO.getCategory());
	        job.setCreateAt(jobDTO.getCreateAt());
	        job.setUpdateAt(jobDTO.getUpdateAt());
	        job.setDeadline(jobDTO.getDeadline());
	        job.setHastag(jobDTO.getHastag());

	        // Set user
	        User user = userRepository.findById(jobDTO.getOwnerId()).orElseThrow(() -> new RuntimeException("User not found"));
	        job.setUser(user);

	        // Set industries
	        List<Industry> industries = new ArrayList<>();
	        for (Long id : jobDTO.getIndustryIds()) {
	            Industry industry = industryRepository.findById(id).orElseThrow(() -> new RuntimeException("Industry not found"));
	            industries.add(industry);
	        }
	        job.setIndustries(industries);

	        // Set positions
	        List<Position> positions = new ArrayList<>();
	        for (Long id : jobDTO.getPositionIds()) {
	            Position position = positionRepository.findById(id).orElseThrow(() -> new RuntimeException("Position not found"));
	            positions.add(position);
	        }
	        job.setPositions(positions);

	        // Set locations
	        List<Location> locations = new ArrayList<>();
	        for (Long id : jobDTO.getLocationIds()) {
	            Location location = locationRepository.findById(id).orElseThrow(() -> new RuntimeException("Location not found"));
	            locations.add(location);
	        }
	        job.setLocations(locations);

	        // Set work types
	        List<WorkType> workTypes = new ArrayList<>();
	        for (Long id : jobDTO.getWorkTypeIds()) {
	            WorkType workType = workTypeRepository.findById(id).orElseThrow(() -> new RuntimeException("WorkType not found"));
	            workTypes.add(workType);
	        }
	        job.setWorkTypes(workTypes);

	        System.out.println("new job: " + job);
	        return jobRepository.save(job);
	    }
	    
	    public List<Job> getJobsByUser(UUID userId) {
	        return jobRepository.findAllByUserId(userId);
	    }
	    
	    
	    public List<Job> getJobsByIndustry(String industryName) {
	        return jobRepository.findByIndustry(industryName);
	    }

	    public List<Job> getJobsByPosition(String positionName) {
	        return jobRepository.findByPosition(positionName);
	    }

	    public List<Job> getJobsByLocation(String locationName) {
	        return jobRepository.findByLocation(locationName);
	    }

	    public List<Job> getJobsByWorkType(String workTypeName) {
	        return jobRepository.findByWorkType(workTypeName);
	    }

	    public List<Job> getJobsByMultipleCategories(String industryName, String positionName, String locationName, String workTypeName) {
	        return jobRepository.findByMultipleCategories(industryName, positionName, locationName, workTypeName);
	    }
}
