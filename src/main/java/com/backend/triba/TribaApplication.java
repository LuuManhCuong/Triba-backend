package com.backend.triba;

import java.util.List;
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
        List<User> adminAccount = userRepository.findByRole(Roles.ADMIN);

        if (adminAccount == null || adminAccount.isEmpty()) {
            userRepository.save(User.builder()
                    .email("admin@gmail.com")
                    .firstName("admin")
                    .lastName("admin")
                    .password(new BCryptPasswordEncoder().encode("admin"))
                    .role(Roles.ADMIN)
                    .build());
        }
    }
}
