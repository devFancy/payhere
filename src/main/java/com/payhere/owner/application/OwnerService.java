package com.payhere.owner.application;

import com.payhere.auth.domain.HashingI;
import com.payhere.owner.domain.CellPhoneNumber;
import com.payhere.owner.domain.OwnerRepository;
import com.payhere.owner.domain.Password;
import com.payhere.owner.domain.entity.Owner;
import com.payhere.owner.dto.SignupRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final HashingI hashing;

    public OwnerService(final OwnerRepository ownerRepository, final HashingI hashing) {
        this.ownerRepository = ownerRepository;
        this.hashing = hashing;
    }

    @Transactional
    public void signUp(final SignupRequest signupRequest) {
        Owner owner = Owner.builder()
                .cellPhoneNumber(new CellPhoneNumber(signupRequest.getCellPhoneNumber()))
                .password(Password.of(hashing, signupRequest.getPassword()))
                .build();
        ownerRepository.save(owner);
    }
}
