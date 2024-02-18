package com.payhere.product.presentation;

import com.payhere.auth.dto.LoginOwner;
import com.payhere.auth.presentation.AuthenticationPrincipal;
import com.payhere.global.ApiResponse;
import com.payhere.product.application.ProductService;
import com.payhere.product.dto.request.ProductCreateRequest;
import com.payhere.product.dto.request.ProductUpdateRequest;
import com.payhere.product.dto.response.ProductDetailResponse;
import com.payhere.product.dto.response.ProductsResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Tag(name = "products", description = "상품 관련 API")
@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products/new")
    public ResponseEntity<ApiResponse<ProductDetailResponse>> save(@Parameter(hidden = true) @AuthenticationPrincipal final LoginOwner loginOwner,
                                                                   @Valid @RequestBody final ProductCreateRequest request) {
        ProductDetailResponse response = productService.save(loginOwner.getId(), request.toServiceRequest());
        ApiResponse<ProductDetailResponse> apiResponse = ApiResponse.created(response);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/products")
    public ResponseEntity<ApiResponse<ProductsResponse>> findAll(@Parameter(hidden = true) @AuthenticationPrincipal final LoginOwner loginOwner,
                                                                 @PageableDefault(size = 10, sort = "createdAt", direction = DESC) final Pageable pageable) {
        ProductsResponse response = productService.findAll(loginOwner.getId(), pageable);
        ApiResponse<ProductsResponse> apiResponse = ApiResponse.ok(response);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ApiResponse<ProductDetailResponse>> findProduct(@Parameter(hidden = true) @AuthenticationPrincipal final LoginOwner loginOwner,
                                                                          @PathVariable final long productId) {
        ProductDetailResponse response = productService.find(loginOwner.getId(), productId);
        ApiResponse<ProductDetailResponse> apiResponse = ApiResponse.ok(response);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/products/search")
    public ResponseEntity<ApiResponse<ProductsResponse>> searchSlicePosts(@Parameter(hidden = true) @AuthenticationPrincipal final LoginOwner loginOwner,
                                                                          @RequestParam @Nullable String query,
                                                                          @PageableDefault(sort = "createdAt", direction = DESC) Pageable pageable) {
        ProductsResponse response = productService.searchSliceWithQuery(loginOwner.getId(), query, pageable);
        ApiResponse<ProductsResponse> apiResponse = ApiResponse.ok(response);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PatchMapping("/products/{productId}")
    public ResponseEntity<ApiResponse<ProductDetailResponse>> update(@Parameter(hidden = true) @AuthenticationPrincipal final LoginOwner loginOwner,
                                                     @PathVariable final Long productId,
                                                     @Valid @RequestBody final ProductUpdateRequest request) {
        productService.update(loginOwner.getId(), productId, request.toServiceRequest());
        ApiResponse<ProductDetailResponse> apiResponse = ApiResponse.noContent();
        return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<ApiResponse<Void>> delete(@Parameter(hidden = true) @AuthenticationPrincipal final LoginOwner loginOwner,
                                    @PathVariable final Long productId) {
        productService.delete(loginOwner.getId(), productId);
        ApiResponse<Void> apiResponse = ApiResponse.noContent();
        return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
    }
}
