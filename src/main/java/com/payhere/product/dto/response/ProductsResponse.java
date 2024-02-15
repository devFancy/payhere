package com.payhere.product.dto.response;


import com.payhere.product.domain.entity.Product;
import lombok.Getter;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ProductsResponse {

    private final List<ProductsElementsResponse> products;

    public ProductsResponse(final List<ProductsElementsResponse> products) {
        this.products = products;
    }

    public static ProductsResponse ofProductSlice(final Slice<Product> productsSlice) {
        List<ProductsElementsResponse> productsElementsResponses = productsSlice.getContent()
                .stream()
                .map(ProductsElementsResponse::from)
                .collect(Collectors.toList());
        return new ProductsResponse(productsElementsResponses);
    }
}
