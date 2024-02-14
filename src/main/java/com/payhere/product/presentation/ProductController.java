package com.payhere.product.presentation;

import com.payhere.auth.dto.LoginOwner;
import com.payhere.auth.presentation.AuthenticationPrincipal;
import com.payhere.global.ApiResponse;
import com.payhere.product.application.ProductService;
import com.payhere.product.dto.request.ProductCreateRequest;
import com.payhere.product.dto.request.ProductUpdateRequest;
import com.payhere.product.dto.response.ProductDetailResponse;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/new")
    public ApiResponse<ProductDetailResponse> saveProduct(@AuthenticationPrincipal final LoginOwner loginOwner,
                                                          @Valid @RequestBody final ProductCreateRequest request) {
        return ApiResponse.ok(productService.createProduct(loginOwner.getId(), request.toServiceRequest()));
    }

    @PatchMapping("/{productId}")
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
