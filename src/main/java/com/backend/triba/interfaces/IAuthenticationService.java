package com.backend.triba.interfaces;

import com.backend.triba.dto.JwtAuthenticationResponse;
import com.backend.triba.dto.RefreshTokenRequestDTO;
import com.backend.triba.dto.SigUpRequestDTO;
import com.backend.triba.dto.SiginRequestDTO;
import com.backend.triba.entities.User;

public interface IAuthenticationService {

	User sigup(SigUpRequestDTO sigUpRequestDTO);
	JwtAuthenticationResponse sigin(SiginRequestDTO siginRequestDTO);
	JwtAuthenticationResponse refreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO);
}
