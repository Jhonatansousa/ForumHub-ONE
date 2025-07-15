package com.one.alura.ForumHub.config;

import com.one.alura.ForumHub.entity.User;
import com.one.alura.ForumHub.entity.UserRole;
import com.one.alura.ForumHub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        String email = "admin@forumhub.com";
        User admin = userRepository.findByEmail(email);

        if (admin == null) {
            admin = new User();
            admin.setName("Administrator");
            admin.setEmail(email);
            admin.setPassword(passwordEncoder.encode("Admin@123"));
            admin.setRole(UserRole.ADMIN);

            userRepository.save(admin);
            System.out.println("Admin Created!");

        }
    }


}
