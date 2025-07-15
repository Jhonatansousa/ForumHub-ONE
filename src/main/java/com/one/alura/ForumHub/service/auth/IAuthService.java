package com.one.alura.ForumHub.service.auth;

import com.one.alura.ForumHub.dto.auth.LoginRequestDTO;
import com.one.alura.ForumHub.dto.auth.RegisterRequestDTO;
import com.one.alura.ForumHub.security.AuthToken;


public interface IAuthService {
    AuthToken login(LoginRequestDTO loginRequestDTO);
    void register(RegisterRequestDTO registerRequestDTO);
}
