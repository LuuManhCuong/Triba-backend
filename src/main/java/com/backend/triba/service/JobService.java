package com.backend.triba.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.backend.triba.dto.JobDTO;
import com.backend.triba.entities.Image;
import com.backend.triba.entities.Industry;
import com.backend.triba.entities.Job;
import com.backend.triba.entities.Location;
import com.backend.triba.entities.Position;
import com.backend.triba.entities.User;
import com.backend.triba.entities.WorkType;
import com.backend.triba.repository.ImageRepository;
import com.backend.triba.repository.IndustryRepository;
import com.backend.triba.repository.JobRepository;
import com.backend.triba.repository.LocationRepository;
import com.backend.triba.repository.PositionRepository;
import com.backend.triba.repository.UserRepository;
import com.backend.triba.repository.WorkTypeRepository;

import jakarta.persistence.criteria.Predicate;

@Service
public class JobService {

	 @Autowired
	    private JobRepository jobRepository;
	 
	 @Autowired
	 private ImageRepository imageRepository;
	 
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
	        if (jobDTO.getIndustries() != null) {
	            List<Industry> industries = new ArrayList<>();
	            for (String name : jobDTO.getIndustries()) {
	                Industry industry = industryRepository.findByName(name)
	                        .orElseThrow(() -> new RuntimeException("Industry not found"));
	                industries.add(industry);
	            }
	            job.setIndustries(industries);
	        }

	        // Set positions
	        if (jobDTO.getPositions() != null) {
	            List<Position> positions = new ArrayList<>();
	            for (String name : jobDTO.getPositions()) {
	                Position position = positionRepository.findByName(name)
	                        .orElseThrow(() -> new RuntimeException("Position not found"));
	                positions.add(position);
	            }
	            job.setPositions(positions);
	        }

	        // Set locations
	        if (jobDTO.getLocations() != null) {
	            List<Location> locations = new ArrayList<>();
	            for (String name : jobDTO.getLocations()) {
	                Location location = locationRepository.findByName(name)
	                        .orElseThrow(() -> new RuntimeException("Location not found"));
	                locations.add(location);
	            }
	            job.setLocations(locations);
	        }

	        // Set work types
	        if (jobDTO.getWorkTypes() != null) {
	            List<WorkType> workTypes = new ArrayList<>();
	            for (String name : jobDTO.getWorkTypes()) {
	                WorkType workType = workTypeRepository.findByName(name)
	                        .orElseThrow(() -> new RuntimeException("WorkType not found"));
	                workTypes.add(workType);
	            }
	            job.setWorkTypes(workTypes);
	        }
	        
	        job.setCreateAt(LocalDate.now());
	        // Save job first
	        Job savedJob = jobRepository.save(job);
	      
	     // Save images
	        if (jobDTO.getImages() != null) {
	            for (String imageUrl : jobDTO.getImages()) {
	            	System.out.println("im: g: " + imageUrl);
	                Image image = new Image();
	                image.setUrl(imageUrl);
	                image.setJob(savedJob);
	                imageRepository.save(image);
	            }
	        }

	        return savedJob;
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

	    public Page<Job> getJobsByMultipleCategories(String industryName, String positionName, String locationName, String workTypeName, int page, int size) {
	        Specification<Job> spec = (root, query, criteriaBuilder) -> {
	            Predicate predicate = criteriaBuilder.conjunction();

	            if (StringUtils.hasText(industryName)) {
	                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.join("industries").get("name"), industryName));
	            }
	            if (StringUtils.hasText(positionName)) {
	                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.join("positions").get("name"), positionName));
	            }
	            if (StringUtils.hasText(locationName)) {
	                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.join("locations").get("name"), locationName));
	            }
	            if (StringUtils.hasText(workTypeName)) {
	                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.join("workTypes").get("name"), workTypeName));
	            }

	            return predicate;
	        };

	        Pageable pageable = PageRequest.of(page, size);
	        return jobRepository.findAll(spec, pageable);
	    }
}
