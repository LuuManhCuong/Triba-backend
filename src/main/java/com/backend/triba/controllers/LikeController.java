package com.backend.triba.controllers;

import com.backend.triba.entities.Like;
import com.backend.triba.service.LikeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user/like")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping
    public boolean addLike(@RequestParam UUID jobId, @RequestParam UUID userId) {
       System.out.println("like: " + jobId +  " ooooooooo  " + userId);
       boolean status=  likeService.toggleLike(jobId, userId);
       return status;
    }

  
}
