package com.payhere.product.domain;

import com.payhere.global.config.JpaAuditingConfig;
import com.payhere.owner.domain.CellPhoneNumber;
import com.payhere.owner.domain.OwnerRepository;
import com.payhere.owner.domain.Password;
import com.payhere.owner.domain.entity.Owner;
import com.payhere.product.domain.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.ActiveProfiles;

import static com.payhere.common.fixtures.ProductFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Import(JpaAuditingConfig.class)
@ActiveProfiles("test")
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    private Product product1;
    private Product product2;
    private Product product3;

    private Owner owner;

    @BeforeEach
    void setUp() {
        owner = new Owner(new CellPhoneNumber("010-1234-5678"), new Password("qwer1234!!"));
        ownerRepository.save(owner);

        product1 = new Product(owner, 상품_카테고리, 상품_가격, 상품_원가, 상품_이름, 상품_설명, 상품_바코드, 상품_만기일, 상품_크기);
        product2 = new Product(owner, 상품_카테고리, 상품_가격, 상품_원가, 상품_이름, 상품_설명, 상품_바코드, 상품_만기일, 상품_크기);
        product3 = new Product(owner, 상품_카테고리, 상품_가격, 상품_원가, 상품_이름, 상품_설명, 상품_바코드, 상품_만기일, 상품_크기);

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
    }

    @DisplayName("등록된 상품과 회원 테이블이 정상적으로 매핑된다.")
    @Test
    void getById() {
        // given
        Product foundProduct = productRepository.getById(product1.getId());

        // when & then
        assertThat(foundProduct.getOwner()).isNotNull();
    }

    @DisplayName("등록된 상품들이 최신순으로 모두 조회된다.")
    @Test
    void findProducts() {
        // given
        Slice<Product> result = productRepository.findProducts(PageRequest.of(0, 3, DESC, "createdAt"));

        // when & then
        assertThat(result.getContent()).containsExactly(product3, product2, product1);
        assertThat(result.getSize()).isEqualTo(3);
    }

    @DisplayName("상품 이름을 기반으로 특정 쿼리에 부합하는 한개 또는 여러개 상품이 조회된다.")
    @Test
    void findProductSlicePages_ByQuery() {
        // given
        Slice<Product> result = productRepository.findProductSlicePagesByQuery(PageRequest.of(0, 3, DESC, "createdAt"), "아메리카노", "ㅇㅁㄹㅋㄴ");

        // when & then
        assertThat(result.getContent()).containsExactly(product3, product2, product1);
        assertThat(result.getSize()).isEqualTo(3);
    }
}