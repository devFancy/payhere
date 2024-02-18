package com.payhere.auth.domain;

import com.payhere.auth.domain.entity.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {

    void deleteAllByOwnerId(final Long ownerId);
}
