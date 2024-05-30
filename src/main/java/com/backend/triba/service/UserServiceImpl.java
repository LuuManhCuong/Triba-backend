package com.backend.triba.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.backend.triba.entities.Comment;
import com.backend.triba.entities.Job;
import com.backend.triba.entities.JobApplication;
import com.backend.triba.entities.Like;
import com.backend.triba.entities.Search;
import com.backend.triba.entities.Token;
import com.backend.triba.entities.User;
import com.backend.triba.interfaces.UserService;
import com.backend.triba.repository.CommentRepository;
import com.backend.triba.repository.JobApplicationRepository;
import com.backend.triba.repository.JobRepository;
import com.backend.triba.repository.LikeRepository;
import com.backend.triba.repository.SearchRepository;
import com.backend.triba.repository.TokenRepository;
import com.backend.triba.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	@Autowired
	private final UserRepository userRepository;

	@Autowired
	private JobApplicationRepository jobApplicationRepository;

	@Autowired
	private LikeRepository likeRepository;

	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private SearchRepository searchRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private JobService jobService;

	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {

			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				return userRepository.findByEmail(username);
			}
		};
	}

	public long getTotalUsers() {
		return userRepository.count();
	}

	public User getUserById(UUID userId) {
		return userRepository.findById(userId).orElse(null);
	}

	public Page<User> getAllUsers(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	public Page<User> searchUsersByName(String keyword, Pageable pageable) {
		return userRepository.searchUsersByName(keyword, pageable);
	}

	public void saveUser(User user) {
		userRepository.save(user);
	}

	public void deleteUserById(UUID userId) {
		// Xóa các liên kết trong bảng JobApplication
		List<JobApplication> jobApplications = jobApplicationRepository.findByUser_UserId(userId);
		for (JobApplication jobApplication : jobApplications) {
			jobApplication.setUser(null);
			jobApplication.setJob(null);
			jobApplicationRepository.save(jobApplication);
		}

		// Xóa các liên kết trong bảng Like
		List<Like> likes = likeRepository.findByUser_UserId(userId);
		for (Like like : likes) {
			like.setJob(null);
			like.setUser(null);
			likeRepository.save(like);
		}

		// Xóa các liên kết trong bảng Token
		List<Token> tokens = tokenRepository.findByUser_UserId(userId);
		for (Token token : tokens) {
			token.setUser(null);
			tokenRepository.save(token);
		}

		List<Search> searchs = searchRepository.findByUser_UserId(userId);
		for (Search search : searchs) {
			search.setUser(null);
			searchRepository.save(search);
		}

		List<Comment> comments = commentRepository.findByUser_UserId(userId);
		for (Comment comment : comments) {
			comment.setJob(null);
			comment.setUser(null);
			commentRepository.save(comment);
		}

		List<Job> jobs = jobRepository.findAllByUserId(userId);
		for (Job job : jobs) {
			jobService.deleteJobById(job.getJobId());
		}

		// Xóa các bản ghi trong các bảng liên kết
		jobApplicationRepository.flush();
		likeRepository.flush();
		tokenRepository.flush();
		searchRepository.flush();
		commentRepository.flush();
		jobRepository.flush();

		// Xóa người dùng
		userRepository.deleteById(userId);
	}
}
