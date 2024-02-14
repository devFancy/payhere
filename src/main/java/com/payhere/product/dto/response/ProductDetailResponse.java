package com.payhere.product.dto.response;

import com.payhere.product.domain.ProductCategory;
import com.payhere.product.domain.ProductSize;
import com.payhere.product.domain.entity.Product;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductDetailResponse {

    private Long id;
    private ProductCategory productCategory;
    private int price;
    private int cost;
    private String name;
    private String description;
    private String barcode;
    private LocalDateTime expirationDate;
    private ProductSize productSize;



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
