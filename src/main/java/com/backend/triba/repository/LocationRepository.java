package com.backend.triba.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.triba.entities.Industry;
import com.backend.triba.entities.Location;
import com.backend.triba.entities.User;
import com.backend.triba.entities.WorkType;

public interface LocationRepository extends JpaRepository<Location, Long> {

	Optional<Location> findByName(String name);

}
