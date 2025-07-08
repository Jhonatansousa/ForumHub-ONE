package com.one.alura.ForumHub.service.auth;

import com.one.alura.ForumHub.dto.LoginRequestDTO;
import com.one.alura.ForumHub.security.AuthToken;


public interface IAuthService {
    public AuthToken login(LoginRequestDTO loginRequestDTO);
}
