package com.backend.triba.controllers;

import java.io.IOException;
import java.util.List;

import org.aspectj.weaver.ast.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.triba.dto.JwtAuthenticationResponse;
import com.backend.triba.dto.RefreshTokenRequestDTO;
import com.backend.triba.dto.SigUpRequestDTO;
import com.backend.triba.dto.SiginRequestDTO;
import com.backend.triba.dto.SignInResponse;
import com.backend.triba.entities.Token;
import com.backend.triba.entities.User;
import com.backend.triba.repository.TokenRepository;
import com.backend.triba.repository.UserRepository;
import com.backend.triba.service.AuthenticationService;
import com.backend.triba.service.JWTServiceImpl;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

	
	
	@Autowired
	private AuthenticationService authenticationService;
	

	
	@PostMapping("/sigup")
	public ResponseEntity<?> sigup(@RequestBody SigUpRequestDTO sigUpRequestDTO) {
		System.out.println(sigUpRequestDTO);
		
		return ResponseEntity.ok(authenticationService.register(sigUpRequestDTO));
	}
	
	@PostMapping("/sigin")
	public ResponseEntity<SignInResponse> sigin(@RequestBody SiginRequestDTO siginRequestDTO) {
		System.out.println(siginRequestDTO);
		return ResponseEntity.ok(authenticationService.authenticate(siginRequestDTO));
	}


	@PostMapping("/refresh")
	public ResponseEntity<?> refresh(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("go here");
		return authenticationService.refreshToken(request, response);
	}
}
