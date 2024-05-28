package com.backend.triba.service;

import com.backend.triba.dto.SearchDTO;
import com.backend.triba.entities.Search;
import com.backend.triba.entities.User;
import com.backend.triba.repository.SearchRepository;
import com.backend.triba.repository.UserRepository;
import com.google.common.base.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class SearchService {

    private final SearchRepository searchRepository;

    @Autowired
    public SearchService(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    @Autowired
    private UserRepository userRepository;
    
    public Search save(SearchDTO searchDTO) {
        Optional<Search> existingSearch = searchRepository.findByUserUserId(searchDTO.getUserId());
        if (existingSearch.isPresent()) {
            // Thực hiện cập nhật thông tin tìm kiếm đã tồn tại
            Search search = existingSearch.get();
            if (searchDTO.getIndustries() != null && !searchDTO.getIndustries().isEmpty()) {
                search.setIndustries(searchDTO.getIndustries());
            }
            if (searchDTO.getPositions() != null && !searchDTO.getPositions().isEmpty()) {
                search.setPositions(searchDTO.getPositions());
            }
            if (searchDTO.getLocations() != null && !searchDTO.getLocations().isEmpty()) {
                search.setLocations(searchDTO.getLocations());
            }
            if (searchDTO.getWorkTypes() != null && !searchDTO.getWorkTypes().isEmpty()) {
                search.setWorkTypes(searchDTO.getWorkTypes());
            }
            if (searchDTO.getKeySearch() != null && !searchDTO.getKeySearch().isEmpty()) {
                search.setKeySearch(searchDTO.getKeySearch());
            }
            search.setCreateAt(LocalDate.now());
            return searchRepository.save(search);
        } else {
            // Thêm thông tin tìm kiếm mới
            Search newSearch = new Search();
        	User user = userRepository.findByUserId(searchDTO.getUserId());
//        	System.out.println("check user; " + user);
        	newSearch.setIndustries(searchDTO.getIndustries());
        	newSearch.setPositions(searchDTO.getPositions());
        	newSearch.setLocations(searchDTO.getLocations());
        	newSearch.setWorkTypes(searchDTO.getWorkTypes());
        	newSearch.setKeySearch(searchDTO.getKeySearch());
        	newSearch.setCreateAt(LocalDate.now());
        	newSearch.setUser(user);
            return searchRepository.save(newSearch);
        }
    }

	public Optional<Search> findByUserId(UUID userId) {
		// TODO Auto-generated method stub
		return searchRepository.findByUserUserId(userId);
	}


}
