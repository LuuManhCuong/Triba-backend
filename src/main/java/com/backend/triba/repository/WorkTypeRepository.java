package com.backend.triba.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.triba.entities.Industry;
import com.backend.triba.entities.WorkType;

public interface WorkTypeRepository extends JpaRepository<WorkType, Long> {

}
