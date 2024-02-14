package com.payhere.product.presentation;

import com.payhere.auth.dto.LoginOwner;
import com.payhere.auth.presentation.AuthenticationPrincipal;
import com.payhere.global.ApiResponse;
import com.payhere.product.application.ProductService;
import com.payhere.product.dto.request.ProductCreateRequest;
import com.payhere.product.dto.response.ProductDetailResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

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
}
