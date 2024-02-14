package com.payhere.product.dto.request;

import com.payhere.owner.domain.entity.Owner;
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
public class ProductCreateServiceRequest {

    private ProductCategory productCategory;
    private int price;
    private int cost;
    private String name;
    private String description;
    private String barcode;
    private LocalDateTime expirationDate;
    private ProductSize productSize;

    @Builder
    public ProductCreateServiceRequest(final ProductCategory productCategory, final int price, final int cost,
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

    public Product toEntity(final Owner owner, final ProductCreateServiceRequest request) {
        return Product.builder()
                .owner(owner)
                .productCategory(request.productCategory)
                .price(request.price)
                .cost(request.cost)
                .name(request.name)
                .description(request.description)
                .barcode(request.barcode)
                .expirationDate(request.expirationDate)
                .productSize(request.productSize)
                .build();
    }
}
