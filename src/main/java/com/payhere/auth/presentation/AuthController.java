package com.payhere.auth.presentation;


import com.payhere.auth.application.AuthService;
import com.payhere.auth.dto.LoginOwner;
import com.payhere.auth.dto.request.LoginRequest;
import com.payhere.auth.dto.response.AccessTokenResponse;
import com.payhere.global.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "auth", description = "인증/인가 관련 API")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AccessTokenResponse>> login(@Valid @RequestBody final LoginRequest loginRequest) {
        LoginOwner loginOwner = authService.login(loginRequest);
        AccessTokenResponse response = authService.generateAccessToken(loginOwner);
        ApiResponse<AccessTokenResponse> apiResponse = ApiResponse.ok(response);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@AuthenticationPrincipal final LoginOwner loginOwner) {
        authService.deleteToken(loginOwner.getId());
        ApiResponse<Void> apiResponse = ApiResponse.noContent();
        return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
    }
}
