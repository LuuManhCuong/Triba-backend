package com.backend.triba.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.triba.entities.Industry;
import com.backend.triba.entities.Location;
import com.backend.triba.entities.Position;
import com.backend.triba.entities.WorkType;
import com.backend.triba.repository.IndustryRepository;
import com.backend.triba.repository.LocationRepository;
import com.backend.triba.repository.PositionRepository;
import com.backend.triba.repository.WorkTypeRepository;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CategoryControllers {

    @Autowired
    private IndustryRepository industryRepository;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private WorkTypeRepository workTypeRepository;
    
    @PostMapping("/categories")
    public ResponseEntity<String> addCategories(@RequestBody Map<String, Map<String, List<String>>> categories) {
        Map<String, List<String>> categoryMap = categories.get("categories");

        if (categoryMap != null) {
            categoryMap.forEach((key, values) -> {
                values.forEach(value -> {
                    switch (key) {
                        case "industries":
                            Industry industry = new Industry();
                            industry.setName(value);
                            industryRepository.save(industry);
                            break;
                        case "positions":
                            Position position = new Position();
                            position.setName(value);
                            positionRepository.save(position);
                            break;
                        case "locations":
                            Location location = new Location();
                            location.setName(value);
                            locationRepository.save(location);
                            break;
                        case "workTypes":
                            WorkType workType = new WorkType();
                            workType.setName(value);
                            workTypeRepository.save(workType);
                            break;
                        
                    }
                });
            });
            return new ResponseEntity<>("Categories added successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid category data", HttpStatus.BAD_REQUEST);
        }
    }
}
