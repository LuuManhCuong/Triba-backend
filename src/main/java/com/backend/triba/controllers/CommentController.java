package com.backend.triba.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.backend.triba.dto.CommentDTO;
import com.backend.triba.entities.Comment;
import com.backend.triba.entities.Job;
import com.backend.triba.entities.User;
import com.backend.triba.repository.CommentRepository;
import com.backend.triba.repository.JobRepository;
import com.backend.triba.repository.UserRepository;

@RestController
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/api/v1/user/comments/{jobId}")
    public ResponseEntity<List<Comment>> getCommentsByJobId(@PathVariable UUID jobId) {
        List<Comment> comments = commentRepository.findByJob_JobIdOrderByCreatedAtDesc(jobId);
        return ResponseEntity.ok(comments);
    }

    @MessageMapping("/comments/{jobId}")
    @SendTo("/api/v1/user/topic/comments/{jobId}")
    public Comment addComment(@Payload CommentDTO commentDTO) {
        Job job = jobRepository.findById(commentDTO.getJobId())
            .orElseThrow();
        User user = userRepository.findById(commentDTO.getUserId())
            .orElseThrow();

        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setJob(job);
        comment.setUser(user);

        Comment savedComment = commentRepository.save(comment);
        
        return savedComment;
    }

}
