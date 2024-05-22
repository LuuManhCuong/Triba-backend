package com.backend.triba.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.triba.entities.Industry;
import com.backend.triba.entities.Position;
import com.backend.triba.entities.User;

public interface PositionRepository extends JpaRepository<Position, Long> {

	Optional<Position> findByName(String name);

}
