package com.payhere.common.fixtures;

import com.payhere.owner.domain.entity.Owner;
import com.payhere.product.domain.ProductCategory;
import com.payhere.product.domain.ProductSize;
import com.payhere.product.domain.entity.Product;

import java.time.LocalDateTime;

public class ProductFixtures {
    /* 상품 1: 아메키라노 */
    public static final ProductCategory 상품_카테고리 = ProductCategory.HANDMADE;
    public static final int 상품_가격 = 2500;
    public static final int 상품_원가 = 500;
    public static final String 상품_이름 = "아메리카노";
    public static final String 상품_설명 = "장인이 직접 제조하는 아메리카노";
    public static final String 상품_바코드 = "1234-56789";
    public static final LocalDateTime 상품_만기일 = LocalDateTime.of(2024, 12, 31, 23, 59);
    public static final ProductSize 상품_크기 = ProductSize.SMALL;

    /* 상품 2: 카페라떼 */
    public static final ProductCategory 상품_카테고리2 = ProductCategory.HANDMADE;
    public static final int 상품_가격2 = 4000;
    public static final int 상품_원가2 = 1000;
    public static final String 상품_이름2 = "카페라떼";
    public static final String 상품_설명2 = "장인이 직접 제조하는 카페라떼";
    public static final String 상품_바코드2 = "9876-54321";
    public static final LocalDateTime 상품_만기일2 = LocalDateTime.of(2024, 12, 31, 23, 59);
    public static final ProductSize 상품_크기2 = ProductSize.LARGE;

    public static Product 아메리카노(final Owner owner) {
        return Product.builder()
                .owner(owner)
                .productCategory(상품_카테고리)
                .price(상품_가격)
                .cost(상품_원가)
                .name(상품_이름)
                .description(상품_설명)
                .barcode(상품_바코드)
                .expirationDate(상품_만기일)
                .productSize(상품_크기)
                .build();

    }

    public static Product 카페라떼(final Owner owner) {
        return Product.builder()
                .owner(owner)
                .productCategory(상품_카테고리2)
                .price(상품_가격2)
                .cost(상품_원가2)
                .name(상품_이름2)
                .description(상품_설명2)
                .barcode(상품_바코드2)
                .expirationDate(상품_만기일2)
                .productSize(상품_크기2)
                .build();

    }
}
