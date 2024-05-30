package com.backend.triba.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.triba.entities.Search;
import com.backend.triba.entities.User;
import com.google.common.base.Optional;

@Repository
public interface SearchRepository extends JpaRepository<Search, UUID> {

//	void deleteByUser(User user);


	Optional<Search> findByUserUserId(UUID userId);

	List<Search> findByUser_UserId(UUID userId);

}
