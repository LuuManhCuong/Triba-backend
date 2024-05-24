package com.backend.triba.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.backend.triba.entities.Image;
import com.backend.triba.entities.Job;

@Repository
public interface ImageRepository extends JpaRepository<Image, UUID> {

	void deleteAllByJob(Job existingJob);
}
