package com.backend.triba.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.backend.triba.dto.JobDTO;
import com.backend.triba.entities.Comment;
import com.backend.triba.entities.Image;
import com.backend.triba.entities.Industry;
import com.backend.triba.entities.Job;
import com.backend.triba.entities.JobApplication;
import com.backend.triba.entities.Location;
import com.backend.triba.entities.Position;
import com.backend.triba.entities.User;
import com.backend.triba.entities.WorkType;
import com.backend.triba.enums.JobStatus;
import com.backend.triba.repository.CommentRepository;
import com.backend.triba.repository.ImageRepository;
import com.backend.triba.repository.IndustryRepository;
import com.backend.triba.repository.JobApplicationRepository;
import com.backend.triba.repository.JobRepository;
import com.backend.triba.repository.LocationRepository;
import com.backend.triba.repository.PositionRepository;
import com.backend.triba.repository.UserRepository;
import com.backend.triba.repository.WorkTypeRepository;

import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;

@Service
public class JobService {

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private ImageRepository imageRepository;

	@Autowired
	private CommentRepository commentRepository;

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

	@Autowired
	private JobApplicationRepository jobApplicationRepository;

	public List<JobApplication> getJobApplicationsByJobId(UUID jobId) {
		return jobApplicationRepository.findByJobJobId(jobId);
	}

	public List<Job> getAllJobs() {
		return jobRepository.findAll();
	}

	public long getTotalJobs() {
		return jobRepository.count();
	}

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
		job.setStatus(JobStatus.HIRING);

		// Set user
		User user = userRepository.findById(jobDTO.getOwnerId())
				.orElseThrow(() -> new RuntimeException("User not found"));
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

	// Method to update job status
	public void updateJobStatus(UUID jobId, JobStatus newStatus) {
		Job job = jobRepository.findById(jobId).orElseThrow(() -> new RuntimeException("Job not found"));
		job.setStatus(newStatus);
		jobRepository.save(job);
	}

	@Transactional
	public void deleteJobById(UUID jobId) {
		Job job = jobRepository.findById(jobId).orElseThrow(() -> new RuntimeException("Job not found"));

		
        // Cập nhật các ứng dụng công việc liên quan để xóa liên kết với user
        List<JobApplication> jobApplications = jobApplicationRepository.findByJobJobId(jobId);
        for (JobApplication jobApplication : jobApplications) {
            jobApplication.setUser(null);  // Xóa liên kết user trong ứng dụng công việc
            jobApplicationRepository.save(jobApplication);  // Lưu lại ứng dụng công việc để cập nhật mối quan hệ
        }

        
        // Đảm bảo các thay đổi đã được commit
        jobRepository.flush();
        commentRepository.flush();
        jobApplicationRepository.flush();
		
		// Xóa các liên kết với công việc
		job.setIndustries(null);
		job.setPositions(null);
		job.setLocations(null);
		job.setWorkTypes(null);
//		job.setComments(comments);
		job.setUser(null); // Đặt user thành null để xóa liên kết

		   // Cập nhật các bình luận liên quan để xóa liên kết với user
        List<Comment> comments = commentRepository.findByJob_JobId(jobId);
        for (Comment comment : comments) {
            comment.setUser(null); 
            comment.setJob(null);// Xóa liên kết user trong bình luận
            commentRepository.save(comment); 
            commentRepository.delete(comment);// Lưu lại bình luận để cập nhật mối quan hệ
        }

		jobRepository.flush();
        commentRepository.flush();
        jobApplicationRepository.flush();
        
		// Lưu lại công việc để cập nhật các mối quan hệ
		jobRepository.save(job);
		
		System.out.println("jobsss: " + job);

		// Xóa công việc
		jobRepository.deleteByJobId(jobId);
	}

	public Job updateJob(UUID jobId, JobDTO jobDTO) {
		Optional<Job> optionalJob = jobRepository.findById(jobId);
		if (optionalJob.isPresent()) {
			Job existingJob = optionalJob.get();

			// Cập nhật các thuộc tính từ jobDTO
			existingJob.setTitle(jobDTO.getTitle());
			existingJob.setDescription(jobDTO.getDescription());
			existingJob.setThumbnail(jobDTO.getThumbnail());
			existingJob.setCompanyName(jobDTO.getCompanyName());
			existingJob.setLogo(jobDTO.getLogo());
			existingJob.setAddress(jobDTO.getAddress());
			existingJob.setSalary(jobDTO.getSalary());
			existingJob.setBudget(jobDTO.getBudget());
			existingJob.setQuantity(jobDTO.getQuantity());
			existingJob.setCategory(jobDTO.getCategory());
			existingJob.setDeadline(jobDTO.getDeadline());
			existingJob.setHastag(jobDTO.getHastag());
			existingJob.setStatus(jobDTO.getStatus());

			// Set industries
			if (jobDTO.getIndustries() != null) {
				List<Industry> industries = new ArrayList<>();
				for (String name : jobDTO.getIndustries()) {
					Industry industry = industryRepository.findByName(name)
							.orElseThrow(() -> new RuntimeException("Industry not found"));
					industries.add(industry);
				}
				existingJob.setIndustries(industries);
			}

			// Set positions
			if (jobDTO.getPositions() != null) {
				List<Position> positions = new ArrayList<>();
				for (String name : jobDTO.getPositions()) {
					Position position = positionRepository.findByName(name)
							.orElseThrow(() -> new RuntimeException("Position not found"));
					positions.add(position);
				}
				existingJob.setPositions(positions);
			}

			// Set locations
			if (jobDTO.getLocations() != null) {
				List<Location> locations = new ArrayList<>();
				for (String name : jobDTO.getLocations()) {
					Location location = locationRepository.findByName(name)
							.orElseThrow(() -> new RuntimeException("Location not found"));
					locations.add(location);
				}
				existingJob.setLocations(locations);
			}

			// Set work types
			if (jobDTO.getWorkTypes() != null) {
				List<WorkType> workTypes = new ArrayList<>();
				for (String name : jobDTO.getWorkTypes()) {
					WorkType workType = workTypeRepository.findByName(name)
							.orElseThrow(() -> new RuntimeException("WorkType not found"));
					workTypes.add(workType);
				}
				existingJob.setWorkTypes(workTypes);
			}

			// Update images
			if (jobDTO.getImages() != null) {
				for (String imageUrl : jobDTO.getImages()) {
					Image image = new Image();
					image.setUrl(imageUrl);
					image.setJob(existingJob);
					imageRepository.save(image);
				}
			}

			return jobRepository.save(existingJob);
		} else {
			return null;
		}
	}

	public Page<Job> getJobsByMultipleCategories(String industryName, String positionName, String locationName,
			String workTypeName, int page, int size) {
		Specification<Job> spec = (root, query, criteriaBuilder) -> {
			Predicate predicate = criteriaBuilder.conjunction();

			if (StringUtils.hasText(industryName)) {
				predicate = criteriaBuilder.and(predicate,
						criteriaBuilder.equal(root.join("industries").get("name"), industryName));
			}
			if (StringUtils.hasText(positionName)) {
				predicate = criteriaBuilder.and(predicate,
						criteriaBuilder.equal(root.join("positions").get("name"), positionName));
			}
			if (StringUtils.hasText(locationName)) {
				predicate = criteriaBuilder.and(predicate,
						criteriaBuilder.equal(root.join("locations").get("name"), locationName));
			}
			if (StringUtils.hasText(workTypeName)) {
				predicate = criteriaBuilder.and(predicate,
						criteriaBuilder.equal(root.join("workTypes").get("name"), workTypeName));
			}

			return predicate;
		};

		Pageable pageable = PageRequest.of(page, size);
		return jobRepository.findAll(spec, pageable);
	}

	public List<Job> searchJobs(String keyword) {
		return jobRepository.findByTitleIgnoreCase(keyword);
	}

}
