package com.payhere.owner.presentation;

import com.payhere.global.ApiResponse;
import com.payhere.owner.application.OwnerService;
import com.payhere.owner.dto.SignupRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "owners", description = "사장님(회원) 관련 API")
@RestController
@RequestMapping("/owners")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(final OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Long>> signUp(@Valid @RequestBody final SignupRequest signupRequest) {
        Long ownerId =  ownerService.signUp(signupRequest);
        ApiResponse<Long> apiResponse = ApiResponse.created(ownerId);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
}
