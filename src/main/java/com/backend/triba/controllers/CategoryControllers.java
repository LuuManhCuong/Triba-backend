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
    public ResponseEntity<String> addCategories(@RequestBody Map<String, Map<String, Object>> categories) {
        Map<String, Object> categoryMap = categories.get("categories");

        if (categoryMap != null) {
            categoryMap.forEach((key, values) -> {
                switch (key) {
                    case "industries":
                        List<String> industries = (List<String>) values;
                        industries.forEach(value -> {
                            Industry industry = new Industry();
                            industry.setName(value);
                            industryRepository.save(industry);
                        });
                        break;
                    case "positions":
                        List<String> positions = (List<String>) values;
                        positions.forEach(value -> {
                            Position position = new Position();
                            position.setName(value);
                            positionRepository.save(position);
                        });
                        break;
                    case "locations":
                        List<Map<String, String>> locations = (List<Map<String, String>>) values;
                        locations.forEach(locationData -> {
                            Location location = new Location();
                            location.setName(locationData.get("name"));
                            location.setThumbnail(locationData.get("thumbnail"));
                            locationRepository.save(location);
                        });
                        break;
                    case "workTypes":
                        List<String> workTypes = (List<String>) values;
                        workTypes.forEach(value -> {
                            WorkType workType = new WorkType();
                            workType.setName(value);
                            workTypeRepository.save(workType);
                        });
                        break;
                }
            });
            return new ResponseEntity<>("Categories added successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid category data", HttpStatus.BAD_REQUEST);
        }
    }
}
