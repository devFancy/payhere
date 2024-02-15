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
import org.springframework.lang.Nullable;
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

    @GetMapping("/products/{productId}")
    public ApiResponse<ProductDetailResponse> findProduct(@AuthenticationPrincipal final LoginOwner loginOwner,
                                                          @PathVariable final long productId) {
        ProductDetailResponse response = productService.find(loginOwner, productId);
        return ApiResponse.ok(response);
    }

    @GetMapping("/products/search")
    public ApiResponse<ProductsResponse> searchSlicePosts(@RequestParam @Nullable String query,
                                                          @PageableDefault(sort = "createdAt", direction = DESC) Pageable pageable) {
        ProductsResponse response = productService.searchSliceWithQuery(query, pageable);
        return ApiResponse.ok(response);
    }

    @PatchMapping("/products/{productId}")
    public ApiResponse<ProductDetailResponse> updateProduct(@AuthenticationPrincipal final LoginOwner loginOwner,
                                                            @PathVariable final Long productId,
                                                            @Valid @RequestBody final ProductUpdateRequest request) {
        productService.updateProduct(loginOwner, productId, request.toServiceRequest());
        return ApiResponse.noContent();
    }

    @DeleteMapping("/products/{productId}")
    public ApiResponse<Void> deleteProduct(@AuthenticationPrincipal final LoginOwner loginOwner,
                                           @PathVariable final Long productId) {
        productService.deleteProduct(loginOwner, productId);
        return ApiResponse.noContent();
    }
}
