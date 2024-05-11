package com.backend.triba.dto;

import lombok.Data;

@Data
public class SigUpRequestDTO {

	private String firstName;
	private String lastName;
	private String email;
	private String password;
}
