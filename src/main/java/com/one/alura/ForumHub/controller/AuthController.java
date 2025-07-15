package com.one.alura.ForumHub.controller;


import com.one.alura.ForumHub.dto.APIResponse;
import com.one.alura.ForumHub.dto.auth.LoginRequestDTO;
import com.one.alura.ForumHub.dto.auth.RegisterRequestDTO;
import com.one.alura.ForumHub.facade.AuthFacade;
import com.one.alura.ForumHub.security.AuthToken;
import com.one.alura.ForumHub.service.auth.IAuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController {

    public static final String SUCCESS = "SUCCESS";

    private final IAuthService service;
    private final AuthFacade facade;


    @PostMapping("/login")
    public ResponseEntity<APIResponse<AuthToken>> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        AuthToken token = service.login(loginRequestDTO);

        APIResponse<AuthToken> apiResponse = APIResponse.<AuthToken>builder()
                .status(SUCCESS)
                .results(token)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<APIResponse<AuthToken>> register(@RequestBody @Valid RegisterRequestDTO request) {

        AuthToken token = facade.registerAndLogin(request);

        APIResponse<AuthToken> apiResponse = APIResponse.<AuthToken>builder()
                .status(SUCCESS)
                .results(token)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

}
