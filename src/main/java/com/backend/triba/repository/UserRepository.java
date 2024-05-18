package com.backend.triba.repository;


import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.backend.triba.entities.User;
import com.backend.triba.enums.Roles;
@Repository
public interface UserRepository extends JpaRepository<User, UUID>{
	boolean existsByEmail(String email);
	User findByEmail(String email);
	
	User findByRole(Roles role);
	
}
