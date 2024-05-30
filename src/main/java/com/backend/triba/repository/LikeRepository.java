package com.backend.triba.repository;

import com.backend.triba.entities.Like;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LikeRepository extends JpaRepository<Like, UUID> {
	boolean existsByUserUserIdAndJobJobId(UUID userId, UUID jobId);

	Like findByUserUserIdAndJobJobId(UUID userId, UUID jobId);

	List<Like> findByUser_UserId(UUID userId);
}
