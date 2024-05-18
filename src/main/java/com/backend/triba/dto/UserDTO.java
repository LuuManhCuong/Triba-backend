package com.backend.triba.dto;

import java.time.LocalDate;

import java.util.UUID;

import com.backend.triba.enums.Roles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	private UUID id;
	private String email;
	private String firstName;
	private String lastName;
	private String phone;
	private String addres;
	private String avatar;
	private Roles role;
	private LocalDate updateAt;
	private LocalDate createAt;
}
