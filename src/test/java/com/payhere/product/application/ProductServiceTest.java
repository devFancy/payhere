package com.payhere.product.application;

import com.payhere.auth.exception.AuthorizationException;
import com.payhere.global.config.JpaAuditingConfig;
import com.payhere.owner.domain.CellPhoneNumber;
import com.payhere.owner.domain.OwnerRepository;
import com.payhere.owner.domain.Password;
import com.payhere.owner.domain.entity.Owner;
import com.payhere.product.domain.ProductRepository;
import com.payhere.product.domain.entity.Product;
import com.payhere.product.dto.request.ProductCreateServiceRequest;
import com.payhere.product.dto.request.ProductUpdateServiceRequest;
import com.payhere.product.dto.response.ProductDetailResponse;
import com.payhere.product.dto.response.ProductsResponse;
import com.payhere.product.exception.NotFoundProductException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static com.payhere.common.fixtures.ProductFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Import(JpaAuditingConfig.class)
@ActiveProfiles("test")
@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @AfterEach
    void tearDown() {
        productRepository.deleteAllInBatch();
        ownerRepository.deleteAllInBatch();
    }

    @DisplayName("로그인한 사장님(회원)은 상품을 등록할 수 있다.")
    @Test
    void saveProduct() {
        // given
        Owner owner = new Owner(new CellPhoneNumber("010-1234-5678"), new Password("qwer1234!!"));
        ownerRepository.save(owner);

        ProductCreateServiceRequest request = getProductCreateServiceRequest();

        // when
        ProductDetailResponse response = productService.save(owner.getId(), request);
        Product actual = productRepository.findById(response.getId()).orElseThrow(() -> new NotFoundProductException());

        // then
        assertAll(
                () -> assertEquals(response.getProductCategory(), actual.getProductCategory()),
                () -> assertEquals(response.getPrice(), actual.getPrice()),
                () -> assertEquals(response.getCost(), actual.getCost()),
                () -> assertEquals(response.getName(), actual.getName()),
                () -> assertEquals(response.getDescription(), actual.getDescription()),
                () -> assertEquals(response.getBarcode(), actual.getBarcode()),
                () -> assertEquals(response.getExpirationDate(), actual.getExpirationDate()),
                () -> assertEquals(response.getProductSize(), actual.getProductSize())
        );
    }

    @DisplayName("로그인한 사장님(회원)은 등록된 상품을 모두 조회할 수 있다.")
    @Test
    void findAllProducts() {
        // given
        Owner owner = new Owner(new CellPhoneNumber("010-1234-5678"), new Password("qwer1234!!"));
        ownerRepository.save(owner);

        ProductCreateServiceRequest request = getProductCreateServiceRequest();
        productService.save(owner.getId(), request);
        productService.save(owner.getId(), request);
        productService.save(owner.getId(), request);

        // when
        Pageable pageable = PageRequest.of(0, 10, DESC, "createdAt");
        ProductsResponse response = productService.findAll(owner.getId(), pageable);

        // then
        assertAll(
                () -> assertThat(response.getProducts()).hasSize(3),
                () -> assertThat(response.getProducts())
                        .extracting("name")
                        .containsExactly("아메리카노", "아메리카노", "아메리카노")
        );
    }

    @DisplayName("로그인한 사장님(회원)은 상품의 상세내역을 조회할 수 있다.")
    @Test
    void findProduct() {
        // given
        Owner owner = new Owner(new CellPhoneNumber("010-1234-5678"), new Password("qwer1234!!"));
        ownerRepository.save(owner);

        Product product = 아메리카노(owner);
        Long saveProductId = productRepository.save(product).getId();

        // when
        ProductDetailResponse response = productService.find(owner.getId(), saveProductId);

        // then
        assertAll(
                () -> assertThat(response.getProductCategory()).isEqualTo(product.getProductCategory()),
                () -> assertThat(response.getPrice()).isEqualTo(product.getPrice()),
                () -> assertThat(response.getCost()).isEqualTo(product.getCost()),
                () -> assertThat(response.getName()).isEqualTo(product.getName()),
                () -> assertThat(response.getDescription()).isEqualTo(product.getDescription()),
                () -> assertThat(response.getBarcode()).isEqualTo(product.getBarcode()),
                () -> assertThat(response.getExpirationDate()).isEqualTo(product.getExpirationDate()),
                () -> assertThat(response.getProductSize()).isEqualTo(product.getProductSize())
        );
    }

    @DisplayName("로그인한 사장님(회원)은 본인이 등록한 상품을 수정할 수 있다.")
    @Test
    void updateProduct() {
        // given
        Owner owner = new Owner(new CellPhoneNumber("010-1234-5678"), new Password("qwer1234!!"));
        ownerRepository.save(owner);

        Product product = 아메리카노(owner);
        Long saveProductId = productRepository.save(product).getId();
        ProductUpdateServiceRequest request = getProductUpdateServiceRequest();

        // when
        productService.update(owner.getId(), saveProductId, request);

        // then
        assertAll(
                () -> assertThat(request.getProductCategory()).isEqualTo(상품_카테고리2),
                () -> assertThat(request.getPrice()).isEqualTo(상품_가격2),
                () -> assertThat(request.getCost()).isEqualTo(상품_원가2),
                () -> assertThat(request.getName()).isEqualTo(상품_이름2),
                () -> assertThat(request.getDescription()).isEqualTo(상품_설명2),
                () -> assertThat(request.getBarcode()).isEqualTo(상품_바코드2),
                () -> assertThat(request.getExpirationDate()).isEqualTo(상품_만기일2),
                () -> assertThat(request.getProductSize()).isEqualTo(상품_크기2)
        );
    }

    @DisplayName("로그인한 사장님(회원)은 본인이 등록한 상품이 아니면 수정할 수 없다.")
    @Test
    void ShouldNotUpdateProductIfNotOwner() {
        // given
        Owner owner = new Owner(new CellPhoneNumber("010-1234-5678"), new Password("qwer1234!!"));
        ownerRepository.save(owner);
        Owner anotherOwner = new Owner(new CellPhoneNumber("010-8765-4321"), new Password("abcd1234!!"));
        ownerRepository.save(anotherOwner);

        Product product = 아메리카노(owner);
        Long saveProductId = productRepository.save(product).getId();
        ProductUpdateServiceRequest request = getProductUpdateServiceRequest();

        // when
        assertThrows(AuthorizationException.class, () -> {
            productService.update(anotherOwner.getId(), saveProductId, request);
        });

        // then
        assertAll( // 상품 카테고리, 상품 만기일은 동일하고, 나머지는 동일하지 않음
                () -> assertThat(product.getProductCategory()).isEqualTo(request.getProductCategory()),
                () -> assertThat(product.getPrice()).isNotEqualTo(request.getPrice()),
                () -> assertThat(product.getCost()).isNotEqualTo(request.getCost()),
                () -> assertThat(product.getName()).isNotEqualTo(request.getName()),
                () -> assertThat(product.getDescription()).isNotEqualTo(request.getDescription()),
                () -> assertThat(product.getBarcode()).isNotEqualTo(request.getBarcode()),
                () -> assertThat(product.getExpirationDate()).isEqualTo(request.getExpirationDate()),
                () -> assertThat(product.getProductSize()).isNotEqualTo(request.getProductSize())
        );
    }

    @DisplayName("로그인한 사장님(회원)은 본인이 등록한 상품을 삭제할 수 있다.")
    @Test
    void deleteProduct() {
        // given
        Owner owner = new Owner(new CellPhoneNumber("010-1234-5678"), new Password("qwer1234!!"));
        ownerRepository.save(owner);

        Product product = 아메리카노(owner);
        Long saveProductId = productRepository.save(product).getId();

        // when
        productService.delete(owner.getId(), saveProductId);
        Optional<Product> foundProduct = productRepository.findById(saveProductId);

        // then
        assertThat(foundProduct).isEmpty();
    }

    @DisplayName("로그인한 사장님(회원)은 본인이 등록한 상품이 아니면 삭제할 수 없다.")
    @Test
    void ShouldNotDeleteProductIfNotOwner() {
        // given
        Owner owner = new Owner(new CellPhoneNumber("010-1234-5678"), new Password("qwer1234!!"));
        ownerRepository.save(owner);
        Owner anotherOwner = new Owner(new CellPhoneNumber("010-8765-4321"), new Password("abcd1234!!"));
        ownerRepository.save(anotherOwner);

        Product product = 아메리카노(owner);
        Long saveProductId = productRepository.save(product).getId();

        // when
        assertThrows(AuthorizationException.class, () -> {
            productService.delete(anotherOwner.getId(), saveProductId);
        });

        // then
        Optional<Product> foundProduct = productRepository.findById(saveProductId);
        assertThat(foundProduct).isPresent();
    }

    private static ProductCreateServiceRequest getProductCreateServiceRequest() {
        ProductCreateServiceRequest request = ProductCreateServiceRequest.builder()
                .productCategory(상품_카테고리)
                .price(상품_가격)
                .cost(상품_원가)
                .name(상품_이름)
                .description(상품_설명)
                .barcode(상품_바코드)
                .expirationDate(상품_만기일)
                .productSize(상품_크기)
                .build();
        return request;
    }

    private static ProductUpdateServiceRequest getProductUpdateServiceRequest() {
        ProductUpdateServiceRequest request = ProductUpdateServiceRequest.builder()
                .productCategory(상품_카테고리2)
                .price(상품_가격2)
                .cost(상품_원가2)
                .name(상품_이름2)
                .description(상품_설명2)
                .barcode(상품_바코드2)
                .expirationDate(상품_만기일2)
                .productSize(상품_크기2)
                .build();
        return request;
    }
}