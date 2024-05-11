package com.backend.triba.controllers;

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
import com.backend.triba.dto.SigUpRequestDTO;
import com.backend.triba.dto.SiginRequestDTO;
import com.backend.triba.entities.User;
import com.backend.triba.service.AuthenticationService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;
	
	@PostMapping("/sigup")
	public ResponseEntity<User> sigup(@RequestBody SigUpRequestDTO sigUpRequestDTO) {
		System.out.println("go here");
		return ResponseEntity.ok(authenticationService.sigup(sigUpRequestDTO));
	}
	
	@PostMapping("/sigin")
	public ResponseEntity<JwtAuthenticationResponse> sigin(@RequestBody SiginRequestDTO siginRequestDTO) {
		System.out.println("go here");
		return ResponseEntity.ok(authenticationService.sigin(siginRequestDTO));
	}
	

	@GetMapping("/test")
	public String test() {
		return "xin  chào";
	}
}
