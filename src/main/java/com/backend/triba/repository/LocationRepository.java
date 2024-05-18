package com.backend.triba.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.triba.entities.Industry;
import com.backend.triba.entities.Location;
import com.backend.triba.entities.WorkType;

public interface LocationRepository extends JpaRepository<Location, Long> {

}
