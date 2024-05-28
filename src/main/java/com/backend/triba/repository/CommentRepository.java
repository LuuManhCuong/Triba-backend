package com.backend.triba.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.triba.entities.Comment;
@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
//	List<Comment> findByJob_JobId(UUID jobId);
	List<Comment> findByJob_JobIdOrderByCreatedAtDesc(UUID jobId);

	List<Comment> findByJob_JobIdOrderByCreatedAtAsc(UUID jobId);

	List<Comment> findByJobJobId(UUID jobId);

	List<Comment> findByUserUserId(UUID userId);

	List<Comment> findByJob_JobId(UUID jobId);
	
	void deleteByJob_JobId(UUID jobId);
	
	void deleteByJobJobId(UUID jobId);

}