package com.payhere.auth.presentation;


import com.payhere.auth.application.AuthService;
import com.payhere.auth.dto.AuthInfo;
import com.payhere.auth.dto.request.LoginRequest;
import com.payhere.auth.dto.response.AccessTokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        AccessTokenResponse authResponse = authService.generateAccessToken(authInfo);
        return ResponseEntity.ok(authResponse);
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(AuthInfo authInfo) {
        authService.deleteToken(authInfo.getId());
        return ResponseEntity.noContent().build();
    }
}
