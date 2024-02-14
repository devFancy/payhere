package com.payhere.auth.domain;

import com.payhere.auth.domain.entity.AccessToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessTokenRepository extends JpaRepository<AccessToken, Long> {

    void deleteAllByOwnerId(final Long ownerId);
}
