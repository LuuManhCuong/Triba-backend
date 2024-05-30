package com.backend.triba.repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.triba.entities.Job;
import com.backend.triba.entities.User;

@Repository
public interface JobRepository extends JpaRepository<Job, UUID> {
	
	
	// Query to find jobs by industry
    @Query("SELECT j FROM Job j JOIN j.industries i WHERE i.name = :industryName")
    List<Job> findByIndustry(@Param("industryName") String industryName);

    // Query to find jobs by position
    @Query("SELECT j FROM Job j JOIN j.positions p WHERE p.name = :positionName")
    List<Job> findByPosition(@Param("positionName") String positionName);

    // Query to find jobs by location
    @Query("SELECT j FROM Job j JOIN j.locations l WHERE l.name = :locationName")
    List<Job> findByLocation(@Param("locationName") String locationName);

    // Query to find jobs by work type
    @Query("SELECT j FROM Job j JOIN j.workTypes w WHERE w.name = :workTypeName")
    List<Job> findByWorkType(@Param("workTypeName") String workTypeName);
    
    @Query("SELECT j FROM Job j JOIN j.industries i JOIN j.positions p JOIN j.locations l JOIN j.workTypes w " +
            "WHERE i.name = :industryName AND p.name = :positionName AND l.name = :locationName AND w.name = :workTypeName")
     List<Job> findByMultipleCategories(@Param("industryName") String industryName,
                                        @Param("positionName") String positionName,
                                        @Param("locationName") String locationName,
                                        @Param("workTypeName") String workTypeName);
    
    @Query("SELECT j FROM Job j WHERE j.user.userId = :userId")
    List<Job> findAllByUserId(@Param("userId") UUID userId);

    
    @Query("SELECT j FROM Job j JOIN j.industries i JOIN j.positions p JOIN j.locations l JOIN j.workTypes w " +
            "WHERE (:industryName IS NULL OR i.name = :industryName) " +
            "AND (:positionName IS NULL OR p.name = :positionName) " +
            "AND (:locationName IS NULL OR l.name = :locationName) " +
            "AND (:workTypeName IS NULL OR w.name = :workTypeName)")
    List<Job> findByMultipleCategories(@Param("industryName") String industryName,
                                       @Param("positionName") String positionName,
                                       @Param("locationName") String locationName,
                                       @Param("workTypeName") String workTypeName,
                                       Sort sort);

	Page<Job> findAll(Specification<Job> spec, Pageable pageable);

	 @Query(value = "SELECT * FROM job WHERE (title COLLATE SQL_Latin1_General_CP1_CI_AI LIKE %:keyword% )", nativeQuery = true)
	    List<Job> findByTitleIgnoreCase(String keyword);

	void deleteByJobId(UUID jobId);




}
