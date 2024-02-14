package com.payhere.product.domain;

public enum ProductCategory {

    COFFEE_BEANS("커피 원두"),
    HANDMADE("제조 음료"),
    BAKERY("베이커리"),
    ETC("기타");

    private final String text;

    ProductCategory(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
