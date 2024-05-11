package com.backend.triba;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.backend.triba.entities.User;
import com.backend.triba.enums.Roles;
import com.backend.triba.repository.UserRepository;

@SpringBootApplication
public class TribaApplication implements CommandLineRunner {
	
	@Autowired
	private UserRepository userRepository;

	
	public static void main(String[] args) {
		SpringApplication.run(TribaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User adminAccount = userRepository.findByRole(Roles.ADMIN);
		if(adminAccount== null) {
			User user = new User();
			user.setEmail("admin@gmail.com");
			user.setFirstName("admin");
			user.setLastName("admin");
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			user.setRole(Roles.ADMIN);
			
			userRepository.save(user);
		}
		
	}
	
	

}
