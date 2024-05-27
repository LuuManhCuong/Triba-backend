package com.backend.triba.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.triba.converter.UserConverter;
import com.backend.triba.dto.JwtAuthenticationResponse;
import com.backend.triba.dto.RefreshTokenRequestDTO;
import com.backend.triba.dto.SigUpRequestDTO;
import com.backend.triba.dto.SiginRequestDTO;
import com.backend.triba.dto.SignInResponse;
import com.backend.triba.dto.UserDTO;
import com.backend.triba.entities.Token;
import com.backend.triba.entities.User;
import com.backend.triba.enums.Roles;
import com.backend.triba.enums.TokenType;
import com.backend.triba.interfaces.IAuthenticationService;
import com.backend.triba.repository.TokenRepository;
import com.backend.triba.repository.UserRepository;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {


	@Autowired
	private UserRepository userrepository;

	@Autowired
	private UserConverter userConverter;
	


	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JWTServiceImpl jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;
	


	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;
	
	@Value("${token.expiration}")
	Long jwtExpirationMs;
	
	@Value("${refresh.token.expiration}")
	Long jwtRefreshExpirationMs;


	
	/**
	 * 
	 * @author NamHH
	 * @param request
	 * @return SignInResponse(accessToken, refreshToken, user)
	 */
	public ResponseEntity<?> register(SigUpRequestDTO request) {
		// Kiểm tra xem email đã tồn tại trong hệ thống chưa
		if (userrepository.existsByEmail(request.getEmail())) {
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("message", "Email " + request.getEmail() + " đã đăng ký!");
			errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
			// Trả về thông báo lỗi khi email đã tồn tại
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
					.body(errorResponse);
		}
		
		
		//lấy và giá trị từ client gửi lên vào obj user mới
		var user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
				.email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
				.role(Roles.USER)
				.createAt(LocalDate.now())
				.updatateAt(LocalDate.now()).build();
		
		var savedUser = userrepository.save(user); //lưu user vào db
		var jwtToken = jwtService.generateToken(user); //Tạo accessToken
		var refreshToken = jwtService.generateRefreshToken(user); //Tạo refreshToken
		saveUserToken(savedUser, jwtToken, refreshToken); //lưu token vào db
		return ResponseEntity.ok(SignInResponse.builder()
				.accessToken(jwtToken)
				.refreshToken(refreshToken)
				.user(userConverter.toDTO(user))
				.message("Đăng ký thành công!")
				.build());
	}

	/**
	 * 
	 * @author NamHH
	 * @param request
	 * @return 
	 * @return SignInResponse(accessToken, refreshToken, user)
	 */
	public SignInResponse authenticate(SiginRequestDTO request) {
		var user = userrepository.findByEmail(request.getEmail());
		System.out.println( "check user: " + user);
		
		if(user== null) {
			return SignInResponse.builder()
					.message("Không tìm thấy tài khoản này, vui lòng nhập lại!")
					.build();
		}
		
		
		//Tìm và hủy tất cả các token đang hoạt động của user này
		//(đảm bảo chỉ duy nhất 1 token hoạt động)
		revokeAllUserTokens(user);
		
		var jwtToken = jwtService.generateToken(user);//Tạo refreshToken
		var refreshToken = jwtService.generateRefreshToken(user); //lưu token vào db
		
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		
		
		
		saveUserToken(user, jwtToken, refreshToken); //Lưu token mới vào db
		return SignInResponse.builder()
				.accessToken(jwtToken)
				.refreshToken(refreshToken)
				.message("Đăng nhập thành công")
				.user(userConverter.toDTO(user))
				.build();
	}
	
	 @Transactional
	    public boolean deleteTokensByUserId(UUID userId) {
	        int deletedCount = tokenRepository.deleteTokensByUserId(userId);
	        return deletedCount > 0;
	    }

	/**
	 * 
	 * @author NamHH
	 * @param user
	 * @param jwtToken
	 * @param refreshToken
	 */
	private void saveUserToken(User user, String jwtToken, String refreshToken) {
		
		var token = Token.builder()
				.user(user)
				.tokenType(TokenType.BEARER)
				.expired(false)
				.revoked(false)
				.token(jwtToken)
				.refreshToken(refreshToken)
				.expirationTokenDate(LocalDateTime.now().plusSeconds(jwtExpirationMs/1000))//1day = 24*60*60
				.expirationRefTokenDate(LocalDateTime.now().plusSeconds(jwtRefreshExpirationMs/1000))//3day = 3*24*60*60
				.build();
		tokenRepository.save(token);
	}

	/**
	 * 
	 * @author NamHH
	 * @param user
	 * 
	 */
	
	@Transactional
	private void revokeAllUserTokens(User user) {
		//Tìm tất cả các token bằng user_id
		tokenRepository.deleteTokensByUserId(user.getUserId());
	}

	/**
	 * 
	 * @author NamHH
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//Lấy giá trị của header Authorization từ request.
		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		final String refreshToken;
		final String userEmail;
		
		//Kiểm tra xem header Authorization có tồn tại và có bắt đầu bằng chuỗi "Bearer " không.
		//Nếu không tồn tại hoặc không bắt đầu bằng "Bearer ", phương thức trả về null
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return null;
		}
		//Bỏ "Bearer " trong chuỗi lấy được từ header
		refreshToken = authHeader.substring(7);
		
		Map<String, Object> errorResponse = new HashMap<>();
		
		//Get userName từ refreshToken 
		try {
			userEmail = jwtService.extractUsername(refreshToken);
			if (userEmail != null) {
				//Tìm kiếm người dùng trong cơ sở dữ liệu dựa trên email. Nếu không tìm thấy người dùng, phương thức sẽ ném một ngoại lệ.
				var user = this.userrepository.findByEmail(userEmail);
				//Check refreshToken đó có hợp lệ không
				if (jwtService.isTokenValid(refreshToken, user)) {
					var newAccessToken = jwtService.generateToken(user);//Tạo token mới
					var newRefreshToken = jwtService.generateRefreshToken(user);//tạo refreshToken mới
					
					//Tìm và hủy tất cả các token đang hoạt động của user này
					revokeAllUserTokens(user);
					
					saveUserToken(user, newAccessToken, newRefreshToken);//lưu token vào db
					
					return ResponseEntity.ok(SignInResponse.builder()
							.accessToken(newAccessToken)
							.refreshToken(newRefreshToken)
							.user(userConverter.toDTO(user))
							.message("Làm mới token thành công!")
							.build());
				}
			}
		} catch (Exception e) {
			errorResponse.put("message", "Token Không hợp lệ!");
			errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.contentType(MediaType.APPLICATION_JSON)
					.body(errorResponse);
		}
		errorResponse.put("message", "Không tìm thấy token!");
		errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.contentType(MediaType.APPLICATION_JSON)
				.body(errorResponse);
	}

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList<>());
	}

	/**
	 * 
	 * @author namHH
	 * @param username
	 * @param newPassword
	 * @return
	 */
	public SignInResponse changePassword(UUID userId, String newPassword) {
		//Tìm kiếm người dùng trong cơ sở dữ liệu dựa trên email.
		//Nếu không tìm thấy người dùng, ném một NoSuchElementException.
		User user = userRepository.findByUserId(userId);
		
		//Thiết lập mật khẩu mới cho người dùng, sau khi mã hóa mật khẩu mới.
		user.setPassword(bcryptEncoder.encode(newPassword));
		user.setUpdatateAt(LocalDate.now());
		userRepository.save(user);//lưu user vào db
		
		var jwtToken = jwtService.generateToken(user);//tạo accesstoken mới
		var refreshToken = jwtService.generateRefreshToken(user);//tạo refreshToken mới
		
		//Tìm và hủy tất cả các token đang hoạt động của user này
		revokeAllUserTokens(user);
		
		saveUserToken(user, jwtToken, refreshToken);//lưu token vào db
		return SignInResponse.builder()
				.accessToken(jwtToken)
				.refreshToken(refreshToken)
				.user(userConverter.toDTO(user))
				.message("Đổi mật khẩu thành công!")
				.build();
	}
	
	
	 public SignInResponse updateUser(UUID userId, UserDTO requestDTO) {
	        User existingUser = userRepository.findById(userId).orElse(null);
	        User checkEmailUser = userrepository.findByEmail(requestDTO.getEmail());
	        if(checkEmailUser.getUserId() != existingUser.getUserId()) {
	        	return SignInResponse.builder()
	    				.message("Email đã tồn tại!")
	    				.build();
	        }
	        if (existingUser != null) {
	            // Cập nhật thông tin người dùng từ requestDTO
	        	existingUser.setPassword(bcryptEncoder.encode(requestDTO.getPassword()));
	        	existingUser.setUpdatateAt(LocalDate.now());
	            existingUser.setFirstName(requestDTO.getFirstName());
	            existingUser.setLastName(requestDTO.getLastName());
	            existingUser.setEmail(requestDTO.getEmail());
	            existingUser.setSlogan(requestDTO.getSlogan());
	            existingUser.setAddress(requestDTO.getAddress());
	            existingUser.setAvatar(requestDTO.getAvatar());
	            existingUser.setCoverImg(requestDTO.getCoverImg());
	            existingUser.setPhoneNumber(requestDTO.getPhoneNumber());
	            existingUser.setEducation(requestDTO.getEducation());
	            existingUser.setExperience(requestDTO.getExperience());
	            // Các thông tin cập nhật khác...
	            
	            
	            System.out.print("update User: "+ existingUser);
	            // Lưu thay đổi vào cơ sở dữ liệu
	            userRepository.save(existingUser);
	            
	    		
	    		var jwtToken = jwtService.generateToken(existingUser);//tạo accesstoken mới
	    		var refreshToken = jwtService.generateRefreshToken(existingUser);//tạo refreshToken mới
	    		
	        	//Tìm và hủy tất cả các token đang hoạt động của user này
	    		revokeAllUserTokens(existingUser);
	    		
	    		saveUserToken(existingUser, jwtToken, refreshToken);//lưu token vào db
	    		return SignInResponse.builder()
	    				.accessToken(jwtToken)
	    				.refreshToken(refreshToken)
	    				.user(userConverter.toDTO(existingUser))
	    				.message("Cập nhật thành công!")
	    				.build();
	        }
	        return null;
	    }
	
}
