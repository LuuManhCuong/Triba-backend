package com.backend.triba.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.triba.entities.Industry;
import com.backend.triba.entities.Position;

public interface PositionRepository extends JpaRepository<Position, Long> {

}
