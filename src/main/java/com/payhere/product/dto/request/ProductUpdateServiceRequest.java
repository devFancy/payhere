package com.payhere.product.dto.request;

import com.payhere.product.domain.ProductCategory;
import com.payhere.product.domain.ProductSize;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductUpdateServiceRequest {

    private ProductCategory productCategory;
    private int price;
    private int cost;
    private String name;
    private String description;
    private String barcode;
    private LocalDateTime expirationDate;
    private ProductSize productSize;

    @Builder
    public ProductUpdateServiceRequest(final ProductCategory productCategory, final int price, final int cost,
                                       final String name, final String description, final String barcode,
                                       final LocalDateTime expirationDate, final ProductSize productSize) {
        this.productCategory = productCategory;
        this.price = price;
        this.cost = cost;
        this.name = name;
        this.description = description;
        this.barcode = barcode;
        this.expirationDate = expirationDate;
        this.productSize = productSize;
    }
}
