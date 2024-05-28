package com.backend.triba.controllers;

import com.backend.triba.dto.SearchDTO;
import com.backend.triba.entities.Search;
import com.backend.triba.service.SearchService;
import com.google.common.base.Optional;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/search")
public class SearchController {

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveSearch(@RequestBody SearchDTO searchDTO) {
    	System.out.println("check : " + searchDTO);
            // Lưu thông tin tìm kiếm
            Search savedSearch = searchService.save(searchDTO);
            return ResponseEntity.ok(savedSearch);
       
    }
    
    @GetMapping("/{userId}")
    public ResponseEntity<?> getSearchByUserId(@PathVariable UUID userId) {
       
            // Lấy thông tin tìm kiếm theo userId
            Optional<Search> searchOptional = searchService.findByUserId(userId);
            if (searchOptional.isPresent()) {
                return ResponseEntity.ok(searchOptional.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        
    }
}
