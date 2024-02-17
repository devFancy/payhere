package com.payhere.product.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.payhere.product.domain.ProductCategory;
import com.payhere.product.domain.ProductSize;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductCreateRequest {

    private ProductCategory productCategory;

    @NotNull(message = "상품 가격을 입력해 주세요.")
    private int price;

    @NotNull(message = "상품 원가를 입력해 주세요.")
    private int cost;

    @NotBlank(message = "상품 이름을 입력해 주세요.")
    private String name;

    @NotBlank(message = "상품 설명을 입력해 주세요.")
    private String description;

    private String barcode;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime expirationDate;

    @NotNull(message = "상품 사이즈를 선택해주세요.")
    private ProductSize productSize;

    @Builder
    public ProductCreateRequest(final ProductCategory productCategory, final int price, final int cost
            , final String name, final String description, final String barcode
            , final LocalDateTime expirationDate, final ProductSize productSize) {
        this.productCategory = productCategory;
        this.price = price;
        this.cost = cost;
        this.name = name;
        this.description = description;
        this.barcode = barcode;
        this.expirationDate = expirationDate;
        this.productSize = productSize;
    }

    public ProductCreateServiceRequest toServiceRequest() {
        return ProductCreateServiceRequest.builder()
                .productCategory(productCategory)
                .price(price)
                .cost(cost)
                .name(name)
                .description(description)
                .barcode(barcode)
                .expirationDate(expirationDate)
                .productSize(productSize)
                .build();
    }
}
