package com.backend.triba.entities;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.backend.triba.enums.Roles;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "[user]")
public class User implements UserDetails{
	@Id
	@Column( unique = true, updatable = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID userId;
	@Column(columnDefinition = "nvarchar(50)")
	private String firstName;
	@Column(columnDefinition = "nvarchar(50)")
	private String lastName;
	private String password;
	private String email;
	private Roles role;
	private String gender;
	@Column(columnDefinition = "ntext")
	private String education;
	@Column(columnDefinition = "ntext")
	private String experience;
	@Column(columnDefinition = "ntext")
	private String skill;
	private String avatar;
	private String phoneNumber;
	private long point;
	private String coverImg;
	@Column(columnDefinition = "ntext")
	private String sologan;
	@Column(columnDefinition = "nvarchar(255)")
	private String address;
	private String scale;
	@Column(columnDefinition = "nvarchar(255)")
	private String industry;
	private String website;
	private int taxCode;
	private LocalDate createAt;
	private LocalDate updatateAt;
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return List.of(new SimpleGrantedAuthority(role.name()));
	}
//	@Override
//	public String getPassword() {
//		// TODO Auto-generated method stub
//		return password;
//	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}
