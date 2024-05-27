package com.backend.triba.converter;



import java.util.Optional;

import org.springframework.stereotype.Component;

import com.backend.triba.entities.User;
import com.backend.triba.dto.UserDTO;




@Component
public class UserConverter {

	
	public UserDTO toDTO(User user) {
		UserDTO result = new UserDTO();
		
		result.setId(user.getUserId());
		result.setEmail(user.getEmail());
		result.setFirstName(user.getFirstName());
		result.setLastName(user.getLastName());
		result.setPhoneNumber(user.getPhoneNumber());
		
		result.setRole(user.getRole());
		result.setAvatar(user.getAvatar());
		result.setCreateAt(user.getCreateAt());
		result.setUpdateAt(user.getUpdatateAt());
		return result;
	}

	public User toEntity(User user, UserDTO  userDTO) {
		user.setFirstName(Optional.ofNullable(userDTO.getFirstName()).orElse(user.getFirstName()));
		user.setLastName(Optional.ofNullable(userDTO.getLastName()).orElse(user.getLastName()));
		user.setEmail(Optional.ofNullable(userDTO.getEmail()).orElse(user.getEmail()));
		user.setPhoneNumber(Optional.ofNullable(userDTO.getPhoneNumber()).orElse(user.getPhoneNumber()));
		user.setAvatar(Optional.ofNullable(userDTO.getAvatar()).orElse(user.getAvatar()));
		user.setAvatar(Optional.ofNullable(userDTO.getAvatar()).orElse(user.getAvatar()));
		user.setRole(Optional.ofNullable(userDTO.getRole()).orElse(user.getRole()));
		return user;
	}

}