package com.backend.triba.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.triba.model.Job;

public interface JobRepository extends JpaRepository<Job, UUID> {

}
