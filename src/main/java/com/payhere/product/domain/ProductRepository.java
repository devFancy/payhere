package com.payhere.product.domain;

import com.payhere.owner.domain.entity.Owner;
import com.payhere.owner.exception.NotFoundOwnerException;
import com.payhere.product.domain.entity.Product;
import com.payhere.product.exception.NotFoundProductException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT p FROM Product p")
    Slice<Product> findProducts(final Pageable pageable);

    default Product getById(final Long productId) {
        return findById(productId)
                .orElseThrow(NotFoundProductException::new);
    }
}
