package com.one.alura.ForumHub.config;

import com.one.alura.ForumHub.entity.Profile;
import com.one.alura.ForumHub.entity.RoleName;
import com.one.alura.ForumHub.entity.User;
import com.one.alura.ForumHub.repository.ProfileRepository;
import com.one.alura.ForumHub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProfileRepository profileRepository;

    @Override
    public void run(String... args) throws Exception {

        for (RoleName roleName : RoleName.values()) {
            profileRepository.findByName(roleName).orElseGet(() -> {
              Profile profile = new Profile();
              profile.setName(roleName);
              return profileRepository.save(profile);
            });

        }

        String email = "admin@forumhub.com";;
        if (userRepository.findByEmail(email) == null) {
            User admin = new User();
            admin.setName("Administrator");
            admin.setEmail(email);
            admin.setPassword(passwordEncoder.encode("Admin@123"));

            Set<Profile> profiles = new HashSet<>(profileRepository.findAll());
            admin.setProfile(profiles);

            userRepository.save(admin);
            System.out.println("Admin Created with all profiles!");

        }
    }


}
