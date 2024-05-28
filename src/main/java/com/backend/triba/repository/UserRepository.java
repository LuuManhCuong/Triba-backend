package com.backend.triba.repository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.backend.triba.entities.User;
import com.backend.triba.enums.Roles;
@Repository
public interface UserRepository extends JpaRepository<User, UUID>{
	boolean existsByEmail(String email);
	User findByEmail(String email);
	
//	User findByRole(Roles role);
	
	 List<User> findByRole(Roles role);
	
	User findByUserId(UUID userId);
	
	 Page<User> findAll(Pageable pageable);
	 
	 @Query(value = "SELECT * FROM [user] WHERE (first_name COLLATE SQL_Latin1_General_CP1_CI_AI LIKE %:keyword% COLLATE SQL_Latin1_General_CP1_CI_AI OR last_name COLLATE SQL_Latin1_General_CP1_CI_AI LIKE %:keyword% COLLATE SQL_Latin1_General_CP1_CI_AI)", nativeQuery = true)
	    Page<User> searchUsersByName(@Param("keyword") String keyword, Pageable pageable);
}
