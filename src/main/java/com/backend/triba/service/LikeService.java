package com.backend.triba.service;

import com.backend.triba.entities.Job;
import com.backend.triba.entities.Like;
import com.backend.triba.entities.User;
import com.backend.triba.repository.JobRepository;
import com.backend.triba.repository.LikeRepository;
import com.backend.triba.repository.UserRepository;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JobRepository jobRepository;

   public boolean toggleLike(UUID jobId, UUID userId) {
        if (likeRepository.existsByUserUserIdAndJobJobId(userId, jobId)) {
            // If the like exists, remove it
            Like like = likeRepository.findByUserUserIdAndJobJobId(userId, jobId);
            System.out.println("check: " + like);
            like.setUser(null);
            like.setJob(null);
            likeRepository.delete(like);
            return false;
            
        } else {
            // If the like does not exist, add it
            User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
            Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("Job not found"));

            Like like = new Like();
            like.setUser(user);
            like.setJob(job);
            likeRepository.save(like);
            return true;
        }
    }
}
