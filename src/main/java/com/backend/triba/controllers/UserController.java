package com.backend.triba.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import com.backend.triba.dto.SignInResponse;
import com.backend.triba.dto.UserDTO;
import com.backend.triba.entities.User;
import com.backend.triba.enums.Roles;
import com.backend.triba.repository.UserRepository;
import com.backend.triba.service.AuthenticationService;
import com.backend.triba.service.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AuthenticationService authenticationService;

    @PatchMapping("/update/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable UUID userId, @RequestBody UserDTO requestDTO) {
        User existingUser = userService.getUserById(userId);
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy người dùng với ID: " + userId);
        }

        SignInResponse updatedUser = authenticationService.updateUser(userId, requestDTO);

        return ResponseEntity.ok(updatedUser);
    }
    
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable UUID userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy người dùng với ID: " + userId);
        }

        return ResponseEntity.ok(user);
    }
    
    
    @GetMapping("/get-all")
    public ResponseEntity<?> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> usersPage = userService.getAllUsers(pageable);
        return ResponseEntity.ok(usersPage);
    }
    
    @GetMapping("/search")
    public ResponseEntity<?> searchUsersByName(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> usersPage = userService.searchUsersByName(keyword, pageable);
        return ResponseEntity.ok(usersPage);
    }
    
    
    // Thêm endpoint để thay đổi vai trò của người dùng
    @PatchMapping("/toggle-role/{userId}")
    public ResponseEntity<?> toggleUserRole(@PathVariable UUID userId) {
        User existingUser = userService.getUserById(userId);
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy người dùng với ID: " + userId);
        }
        
        Roles currentRole = existingUser.getRole();
        Roles newRole = currentRole == Roles.ADMIN? Roles.USER: Roles.ADMIN;
        existingUser.setRole(newRole);
        userService.saveUser(existingUser);

        return ResponseEntity.ok("Vai trò của người dùng đã được thay đổi thành công thành: " + newRole);
    }

}