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
    public ApiResponse<ProductDetailResponse> save(@AuthenticationPrincipal final LoginOwner loginOwner,
                                                   @Valid @RequestBody final ProductCreateRequest request) {
        return ApiResponse.ok(productService.save(loginOwner.getId(), request.toServiceRequest()));
    }

    @GetMapping("/products")
    public ApiResponse<ProductsResponse> findAll(@AuthenticationPrincipal final LoginOwner loginOwner,
                                                 @PageableDefault(size = 10, sort = "createdAt", direction = DESC) final Pageable pageable) {
        ProductsResponse response = productService.findAll(loginOwner.getId(), pageable);
        return ApiResponse.ok(response);
    }

    @GetMapping("/products/{productId}")
    public ApiResponse<ProductDetailResponse> findProduct(@AuthenticationPrincipal final LoginOwner loginOwner,
                                                          @PathVariable final long productId) {
        ProductDetailResponse response = productService.find(loginOwner.getId(), productId);
        return ApiResponse.ok(response);
    }

    @GetMapping("/products/search")
    public ApiResponse<ProductsResponse> searchSlicePosts(@AuthenticationPrincipal final LoginOwner loginOwner,
                                                          @RequestParam @Nullable String query,
                                                          @PageableDefault(sort = "createdAt", direction = DESC) Pageable pageable) {
        ProductsResponse response = productService.searchSliceWithQuery(loginOwner.getId(), query, pageable);
        return ApiResponse.ok(response);
    }

    @PatchMapping("/products/{productId}")
    public ApiResponse<ProductDetailResponse> update(@AuthenticationPrincipal final LoginOwner loginOwner,
                                                     @PathVariable final Long productId,
                                                     @Valid @RequestBody final ProductUpdateRequest request) {
        productService.update(loginOwner.getId(), productId, request.toServiceRequest());
        return ApiResponse.noContent();
    }

    @DeleteMapping("/products/{productId}")
    public ApiResponse<Void> delete(@AuthenticationPrincipal final LoginOwner loginOwner,
                                    @PathVariable final Long productId) {
        productService.delete(loginOwner.getId(), productId);
        return ApiResponse.noContent();
    }
}
