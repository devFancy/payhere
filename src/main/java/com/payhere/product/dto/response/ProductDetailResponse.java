package com.payhere.product.dto.response;

import com.payhere.product.domain.ProductCategory;
import com.payhere.product.domain.ProductSize;
import com.payhere.product.domain.entity.Product;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProductDetailResponse {

    private final Long id;
    private final ProductCategory productCategory;
    private final int price;
    private final int cost;
    private final String name;
    private final String description;
    private final String barcode;
    private final LocalDateTime expirationDate;
    private final ProductSize productSize;

    @Builder
    public ProductDetailResponse(final Long id, final ProductCategory productCategory, final int price, final int cost,
                                 final String name, final String description, final String barcode,
                                 final LocalDateTime expirationDate, final ProductSize productSize) {
        this.id = id;
        this.productCategory = productCategory;
        this.price = price;
        this.cost = cost;
        this.name = name;
        this.description = description;
        this.barcode = barcode;
        this.expirationDate = expirationDate;
        this.productSize = productSize;
    }

    public static ProductDetailResponse of(final Product product) {
        return ProductDetailResponse.builder()
                .id(product.getId())
                .productCategory(product.getProductCategory())
                .price(product.getPrice())
                .cost(product.getCost())
                .name(product.getName())
                .description(product.getDescription())
                .barcode(product.getBarcode())
                .expirationDate(product.getExpirationDate())
                .productSize(product.getProductSize())
                .build();
    }
}
