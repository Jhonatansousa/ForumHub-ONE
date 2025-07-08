package com.one.alura.ForumHub.controller;


import com.one.alura.ForumHub.dto.APIResponse;
import com.one.alura.ForumHub.dto.LoginRequestDTO;
import com.one.alura.ForumHub.security.AuthToken;
import com.one.alura.ForumHub.service.auth.IAuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController {

    public static final String SUCCESS = "SUCCESS";

    private final IAuthService service;


    @PostMapping("/login")
    public ResponseEntity<APIResponse<AuthToken>> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        AuthToken tk = service.login(loginRequestDTO);

        APIResponse<AuthToken> apiResponse = APIResponse.<AuthToken>builder()
                .status(SUCCESS)
                .results(tk)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

//    @PostMapping("/register")
//    public ResponseEntity<APIResponse> register(@RequestBody @Valid RegisterRequestDTO request) {
//        return null;
//    }

}
