package com.one.alura.ForumHub.service.auth;

import com.one.alura.ForumHub.dto.auth.LoginRequestDTO;
import com.one.alura.ForumHub.dto.TokenDataDTO;
import com.one.alura.ForumHub.dto.auth.RegisterRequestDTO;
import com.one.alura.ForumHub.entity.User;
import com.one.alura.ForumHub.exception.DuplicatedContentException;
import com.one.alura.ForumHub.exception.InvalidCredentialsException;
import com.one.alura.ForumHub.mapper.AuthMapper;
import com.one.alura.ForumHub.repository.UserRepository;
import com.one.alura.ForumHub.security.AuthToken;
import com.one.alura.ForumHub.security.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService{

    private final UserRepository repo;
    private final AuthMapper authMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthToken login(LoginRequestDTO dto)  {
        User res = repo.findByEmail(dto.getEmail());
        if(res == null || !passwordEncoder.matches(dto.getPassword(), res.getPassword())) {
            throw new InvalidCredentialsException("Email or Password Invalid");
        }
        Set<String> roles = res.getProfile().stream()
                .map(profile -> profile.getName().name()).collect(Collectors.toSet());

        TokenDataDTO tokenData = new TokenDataDTO(res.getEmail(), roles);

        return TokenUtil.encodeToken(tokenData);
    }


    @Override
    public void register(RegisterRequestDTO request) {
        if (repo.findByEmail(request.getEmail()) != null) {
            throw new DuplicatedContentException("Email already exists");
        }
        repo.save(authMapper.toEntity(request));
    }



}
