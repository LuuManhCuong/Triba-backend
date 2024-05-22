package com.backend.triba.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.triba.entities.Industry;
import com.backend.triba.entities.User;

public interface IndustryRepository extends JpaRepository<Industry, Long> {

	Optional<Industry> findByName(String name);

}
