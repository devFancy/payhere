package com.payhere.auth.presentation;


import com.payhere.auth.application.AuthService;
import com.payhere.auth.dto.LoginOwner;
import com.payhere.auth.dto.request.LoginRequest;
import com.payhere.auth.dto.response.AccessTokenResponse;
import com.payhere.global.ApiResponse;
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
    public ApiResponse<AccessTokenResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginOwner loginOwner = authService.login(loginRequest);
        AccessTokenResponse authResponse = authService.generateAccessToken(loginOwner);
        return ApiResponse.ok(authResponse);
    }

    @GetMapping("/logout")
    public ApiResponse<Void> logout(@AuthenticationPrincipal LoginOwner loginOwner) {
        authService.deleteToken(loginOwner.getId());
        return ApiResponse.noContent();
    }
}
