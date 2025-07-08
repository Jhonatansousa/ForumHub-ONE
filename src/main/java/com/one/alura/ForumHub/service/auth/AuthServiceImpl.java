package com.one.alura.ForumHub.service.auth;

import com.one.alura.ForumHub.dto.LoginRequestDTO;
import com.one.alura.ForumHub.dto.TokenDataDTO;
import com.one.alura.ForumHub.entity.User;
import com.one.alura.ForumHub.exception.UserServiceBusinessException;
import com.one.alura.ForumHub.repository.UserRepository;
import com.one.alura.ForumHub.security.AuthToken;
import com.one.alura.ForumHub.security.TokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements IAuthService{

    private final UserRepository repo;

    @Override
    public AuthToken login(LoginRequestDTO dto)  {
        User res = repo.findByEmail(dto.getEmail());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(res == null || !passwordEncoder.matches(dto.getPassword(), res.getPassword())) {
            throw new UserServiceBusinessException("Email or Password Invalid");
        }

        TokenDataDTO tokenData = new TokenDataDTO(res.getEmail(), res.getRole());

        return TokenUtil.encodeToken(tokenData);
    }
}
