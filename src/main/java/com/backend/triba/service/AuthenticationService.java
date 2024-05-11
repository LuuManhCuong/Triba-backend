package com.backend.triba.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.triba.dto.JwtAuthenticationResponse;
import com.backend.triba.dto.SigUpRequestDTO;
import com.backend.triba.dto.SiginRequestDTO;
import com.backend.triba.entities.User;
import com.backend.triba.enums.Roles;
import com.backend.triba.interfaces.IAuthenticationService;
import com.backend.triba.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

	
	private final UserRepository userRepository;

	private final  PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JWTServiceImpl jwtService;
	
	
	public User sigup(SigUpRequestDTO sigUpRequestDTO) {
		User user = new User();
		
		user.setEmail(sigUpRequestDTO.getEmail());
		user.setFirstName(sigUpRequestDTO.getFirstName());
		user.setLastName(sigUpRequestDTO.getLastName());
		user.setPassword(passwordEncoder.encode(sigUpRequestDTO.getPassword()));
		user.setRole(Roles.USER);
		
		return userRepository.save(user);
	}
	
	
	public JwtAuthenticationResponse sigin(SiginRequestDTO siginRequestDTO) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(siginRequestDTO.getEmail(), siginRequestDTO.getPassword()));
		
		// Tìm kiếm người dùng trong cơ sở dữ liệu
	    UserDetails user = userRepository.findByEmail(siginRequestDTO.getEmail());
	    
	    // Kiểm tra xem người dùng có tồn tại không
	    if (user == null) {
	        throw new RuntimeException("Email hoặc tài khoản không hợp lệ");
	    }
	    
	    var jwt = jwtService.generateToken(user);
	    var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
	    
	    JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
	    jwtAuthenticationResponse.setToken(jwt);
	    jwtAuthenticationResponse.setRefreshToken((String) refreshToken);
	    
	    return jwtAuthenticationResponse;
	}
}
