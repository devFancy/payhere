package com.payhere.product.dto.response;

import com.payhere.product.domain.ProductSize;
import com.payhere.product.domain.entity.Product;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProductsElementsResponse {

    private final Long id;
    private final int price;
    private final String name;
    private final LocalDateTime expirationDate;
    private final ProductSize productSize;
    private final LocalDateTime createdAt;

    @Builder
    public ProductsElementsResponse(final Long id, final int price, final String name,
                                    final LocalDateTime expirationDate, final ProductSize productSize,
                                    final LocalDateTime createdAt) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.expirationDate = expirationDate;
        this.productSize = productSize;
        this.createdAt = createdAt;
    }


    public static ProductsElementsResponse from(final Product product) {
        return ProductsElementsResponse.builder()
                .id(product.getId())
                .price(product.getPrice())
                .name(product.getName())
                .expirationDate(product.getExpirationDate())
                .productSize(product.getProductSize())
                .createdAt(product.getCreatedAt())
                .build();
    }
}
