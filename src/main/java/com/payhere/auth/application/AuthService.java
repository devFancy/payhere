package com.payhere.auth.application;

import com.payhere.auth.domain.AccessTokenRepository;
import com.payhere.auth.domain.AuthAccessToken;
import com.payhere.auth.domain.hashing.HashingI;
import com.payhere.auth.dto.LoginOwner;
import com.payhere.auth.dto.request.LoginRequest;
import com.payhere.auth.dto.response.AccessTokenResponse;
import com.payhere.auth.exception.LoginFailedException;
import com.payhere.owner.domain.OwnerRepository;
import com.payhere.owner.domain.entity.Owner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class AuthService {

    private final OwnerRepository ownerRepository;
    private final HashingI hashing;
    private final TokenCreator tokenCreator;
    private final AccessTokenRepository accessTokenRepository;

    public AuthService(final OwnerRepository ownerRepository, final HashingI hashing
            , final TokenCreator tokenCreator, AccessTokenRepository accessTokenRepository) {
        this.ownerRepository = ownerRepository;
        this.hashing = hashing;
        this.tokenCreator = tokenCreator;
        this.accessTokenRepository = accessTokenRepository;
    }

    public LoginOwner login(final LoginRequest loginRequest) {
        String cellPhoneNumber = loginRequest.getCellPhoneNumber();
        String password = hashing.generateSHA256Hash(loginRequest.getPassword());
        Owner owner = ownerRepository.findByCellPhoneNumberAndPassword(cellPhoneNumber, password)
                .orElseThrow(LoginFailedException::new);
        return new LoginOwner(owner.getId());
    }

    @Transactional
    public AccessTokenResponse generateAccessToken(final LoginOwner loginOwner) {
        AuthAccessToken authAccessToken = tokenCreator.createAuthToken(loginOwner.getId());
        return new AccessTokenResponse(authAccessToken.getAccessToken());
    }

    @Transactional
    public void deleteToken(final Long ownerId) {
        accessTokenRepository.deleteAllByOwnerId(ownerId);
    }

    public Long extractOwnerId(final String accessToken) {
        Long ownerId = tokenCreator.extractPayLoad(accessToken);
    }
}
