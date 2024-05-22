package com.backend.triba.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.triba.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
//	List<Comment> findByJob_JobId(UUID jobId);
	List<Comment> findByJob_JobIdOrderByCreatedAtDesc(UUID jobId);

	List<Comment> findByJob_JobIdOrderByCreatedAtAsc(UUID jobId);
}