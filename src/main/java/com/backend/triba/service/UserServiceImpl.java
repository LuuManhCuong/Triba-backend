package com.backend.triba.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.triba.dto.UserDTO;
import com.backend.triba.entities.User;
import com.backend.triba.interfaces.UserService;
import com.backend.triba.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	@Autowired
	private final UserRepository userRepository;

	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {
			
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				// TODO Auto-generated method stub
				return userRepository.findByEmail(username);
			}
		};
	};
	
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
	    
}
