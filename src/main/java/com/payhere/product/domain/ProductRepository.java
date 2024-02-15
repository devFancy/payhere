package com.payhere.product.domain;

import com.payhere.product.domain.entity.Product;
import com.payhere.product.exception.NotFoundProductException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT p FROM Product p")
    Slice<Product> findProducts(final Pageable pageable);

    default Product getById(final Long productId) {
        return findById(productId)
                .orElseThrow(NotFoundProductException::new);
    }

    @Query(value = "SELECT p FROM Product p WHERE p.name LIKE %:query% OR p.nameInit LIKE %:queryForChosung%")
    Slice<Product> findProductSlicePagesByQuery(Pageable pageable, @Param("query") String query, @Param("queryForChosung") String queryForChosung);
}
