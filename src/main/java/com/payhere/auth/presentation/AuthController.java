package com.payhere.auth.presentation;


import com.payhere.auth.application.AuthService;
import com.payhere.auth.dto.AuthInfo;
import com.payhere.auth.dto.request.LoginRequest;
import com.payhere.auth.dto.response.AccessTokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        AuthInfo authInfo = authService.login(loginRequest);
    }
}
