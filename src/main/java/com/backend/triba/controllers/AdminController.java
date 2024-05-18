package com.backend.triba.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

	@GetMapping
	public ResponseEntity<String> sayHello() {
		return ResponseEntity.ok("admin xin chào");
	}
	
	

}
