package com.payhere.product.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payhere.auth.application.AuthService;
import com.payhere.product.application.ProductService;
import com.payhere.product.dto.request.ProductCreateRequest;
import com.payhere.product.dto.request.ProductUpdateRequest;
import com.payhere.product.dto.response.ProductsElementsResponse;
import com.payhere.product.dto.response.ProductsResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static com.payhere.common.fixtures.OwnerFixtures.사장님;
import static com.payhere.common.fixtures.ProductFixtures.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@WebMvcTest(controllers = {
        ProductController.class,
})
@ActiveProfiles("test")
class ProductControllerTest {
    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private static final String AUTHORIZATION_HEADER_VALUE = "Bearer aaaaaaaa.bbbbbbbb.cccccccc";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @MockBean
    private AuthService authService;

    @DisplayName("사장님은 상품을 등록할 수 있다.")
    @Test
    void saveProduct() throws Exception {
        // given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .productCategory(상품_카테고리)
                .price(상품_가격)
                .cost(상품_원가)
                .name(상품_이름)
                .description(상품_설명)
                .barcode(상품_바코드)
                .expirationDate(상품_만기일)
                .productSize(상품_크기)
                .build();

        // when & then
        mockMvc.perform(post("/products/new")
                        .header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request.toServiceRequest()))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("사장님은 등록된 상품의 리스트를 모두 볼 수 있다.")
    @Test
    void findAllProducts() throws Exception {
        // given
        List<ProductsElementsResponse> responses = Collections.singletonList(ProductsElementsResponse.builder()
                .id(사장님().getId())
                .price(상품_가격)
                .name(상품_이름)
                .expirationDate(상품_만기일)
                .productSize(상품_크기)
                .createdAt(LocalDateTime.now())
                .build());

        given(productService.findAll(사장님().getId(), PageRequest.of(0, 10))).willReturn(new ProductsResponse(responses));

        // when & then
        mockMvc.perform(get("/products")
                        .header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(responses))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("사장님은 상품의 속성을 부분 수정할 수 있다.")
    @Test
    void updateProduct() throws Exception {
        // given
        Long productId = 1L;
        willDoNothing()
                .given(productService)
                .update(any(), any(), any());

        ProductUpdateRequest request = ProductUpdateRequest.builder()
                .productCategory(상품_카테고리2)
                .price(상품_가격2)
                .cost(상품_원가2)
                .name(상품_이름2)
                .barcode(상품_바코드2)
                .name(상품_이름2)
                .description(상품_설명2)
                .expirationDate(상품_만기일2)
                .productSize(상품_크기2)
                .build();

        // when & then
        mockMvc.perform(patch("/products/{productId}", productId)
                        .header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @DisplayName("사장님은 상품을 삭제할 수 있다.")
    @Test
    void deleteProduct() throws Exception {
        // given
        Long productId = 1L;
        willDoNothing()
                .given(productService)
                .update(any(), any(), any());

        // when & then
        mockMvc.perform(delete("/products/{productId}", productId)
                        .header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}