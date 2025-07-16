package com.one.alura.ForumHub.mapper;

import com.one.alura.ForumHub.dto.auth.RegisterRequestDTO;
import com.one.alura.ForumHub.entity.User;
import com.one.alura.ForumHub.entity.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthMapper {

    private final PasswordEncoder passwordEncoder;

    public User toEntity(RegisterRequestDTO request) {
        User newUser = new User();
        newUser.setName(request.getName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole(UserRole.USER);
        return newUser;
    }
}
