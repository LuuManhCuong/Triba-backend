package com.backend.triba.dto;

import com.backend.triba.enums.Roles;

import lombok.Data;

@Data
public class SigUpRequestDTO {

	private String firstName;
	private String lastName;
	private String email;
	private String password;
}
