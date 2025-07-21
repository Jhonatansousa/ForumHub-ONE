package com.one.alura.ForumHub.mapper;

import com.one.alura.ForumHub.dto.auth.RegisterRequestDTO;
import com.one.alura.ForumHub.entity.Profile;
import com.one.alura.ForumHub.entity.RoleName;
import com.one.alura.ForumHub.entity.User;
import com.one.alura.ForumHub.exception.ResourceNotFoundException;
import com.one.alura.ForumHub.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthMapper {

    private final PasswordEncoder passwordEncoder;
    private final ProfileRepository profileRepository;

    public User toEntity(RegisterRequestDTO request) {
        User newUser = new User();
        newUser.setName(request.getName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        //busco o tipo do perfil
        Profile userProfile = profileRepository.findByName(RoleName.USER)
                .orElseThrow(() -> new ResourceNotFoundException("Profile 'USER' not found"));
        //adiciono dessa forma por causa do HashSet
        newUser.getProfile().add(userProfile);
        return newUser;
    }
}
