package com.payhere.owner.presentation;

import com.payhere.owner.application.OwnerService;
import com.payhere.owner.dto.SignupRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/owners")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(final OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@Valid @RequestBody SignupRequest signupRequest) {
        ownerService.signUp(signupRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
