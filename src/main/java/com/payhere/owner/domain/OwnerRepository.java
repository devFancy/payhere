package com.payhere.owner.domain;

import com.payhere.owner.domain.entity.Owner;
import com.payhere.owner.exception.NotFoundOwnerException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

    @Query("SELECT o " +
            "FROM Owner o " +
            "WHERE o.cellPhoneNumber.value = :cellPhoneNumber AND o.password.value = :password")
    Optional<Owner> findByCellPhoneNumberAndPassword(@Param("cellPhoneNumber") final String cellPhoneNumber, @Param("password") final String password);

    default void validateExistById(final Long ownerId) {
        if (!existsById(ownerId)) {
            throw new NotFoundOwnerException();
        }
    }

    default Owner getById(final Long id) {
        return findById(id)
                .orElseThrow(NotFoundOwnerException::new);
    }
}
