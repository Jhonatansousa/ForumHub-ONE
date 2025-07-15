package com.one.alura.ForumHub.service.auth;

import com.one.alura.ForumHub.dto.auth.LoginRequestDTO;
import com.one.alura.ForumHub.dto.TokenDataDTO;
import com.one.alura.ForumHub.dto.auth.RegisterRequestDTO;
import com.one.alura.ForumHub.entity.User;
import com.one.alura.ForumHub.entity.UserRole;
import com.one.alura.ForumHub.exception.UserServiceBusinessException;
import com.one.alura.ForumHub.repository.UserRepository;
import com.one.alura.ForumHub.security.AuthToken;
import com.one.alura.ForumHub.security.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService{

    private final UserRepository repo;

    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthToken login(LoginRequestDTO dto)  {
        User res = repo.findByEmail(dto.getEmail());
        if(res == null || !passwordEncoder.matches(dto.getPassword(), res.getPassword())) {
            throw new UserServiceBusinessException("Email or Password Invalid");
        }

        TokenDataDTO tokenData = new TokenDataDTO(res.getEmail(), res.getRole());

        return TokenUtil.encodeToken(tokenData);
    }


    @Override
    public void register(RegisterRequestDTO request) {
        if (repo.findByEmail(request.getEmail()) != null) {
            throw new UserServiceBusinessException("Email already exists");
        }
        User newUser = new User();
        newUser.setName(request.getName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole(UserRole.USER);

        repo.save(newUser);

    }



}
