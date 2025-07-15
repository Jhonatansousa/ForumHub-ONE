package com.one.alura.ForumHub.facade;

import com.one.alura.ForumHub.dto.auth.LoginRequestDTO;
import com.one.alura.ForumHub.dto.auth.RegisterRequestDTO;
import com.one.alura.ForumHub.security.AuthToken;
import com.one.alura.ForumHub.service.auth.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthFacade {
    private final IAuthService authService;

    public AuthToken registerAndLogin(RegisterRequestDTO request) {
        authService.register(request);
        return authService.login(new LoginRequestDTO(request.getEmail(), request.getPassword()));
    }

}
