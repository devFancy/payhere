package com.payhere.product.presentation;

import com.payhere.auth.dto.LoginOwner;
import com.payhere.auth.presentation.AuthenticationPrincipal;
import com.payhere.global.ApiResponse;
import com.payhere.product.application.ProductService;
import com.payhere.product.dto.request.ProductCreateRequest;
import com.payhere.product.dto.request.ProductUpdateRequest;
import com.payhere.product.dto.response.ProductDetailResponse;
import com.payhere.product.dto.response.ProductsResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products/new")
    public ApiResponse<ProductDetailResponse> saveProduct(@AuthenticationPrincipal final LoginOwner loginOwner,
                                                          @Valid @RequestBody final ProductCreateRequest request) {
        return ApiResponse.ok(productService.createProduct(loginOwner.getId(), request.toServiceRequest()));
    }

    @GetMapping("/products")
    public ApiResponse<ProductsResponse> findAll(@PageableDefault(size = 10, sort = "createdAt", direction = DESC) final Pageable pageable) {
        ProductsResponse response = productService.findAll(pageable);
        return ApiResponse.ok(response);
    }

    public ApiResponse<ProductDetailResponse> updateProduct(@AuthenticationPrincipal final LoginOwner loginOwner,
                                                            @Valid @PathVariable final Long productId,
                                                            @RequestBody final ProductUpdateRequest request) {
        productService.updateProduct(loginOwner, productId, request.toServiceRequest());
        return ApiResponse.noContent();
    }

    @DeleteMapping("/{productId}")
    public ApiResponse<Void> deleteProduct(@AuthenticationPrincipal final LoginOwner loginOwner,
                                           @Valid @PathVariable final Long productId) {
        productService.deleteProduct(loginOwner, productId);
        return ApiResponse.noContent();
    }
}
