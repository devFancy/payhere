package com.payhere.product.application;


import com.payhere.owner.domain.OwnerRepository;
import com.payhere.owner.domain.entity.Owner;
import com.payhere.owner.exception.NotFoundOwnerException;
import com.payhere.product.domain.ProductRepository;
import com.payhere.product.domain.entity.Product;
import com.payhere.product.dto.request.ProductCreateServiceRequest;
import com.payhere.product.dto.response.ProductDetailResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final OwnerRepository ownerRepository;

    public ProductService(final ProductRepository productRepository, final OwnerRepository ownerRepository) {
        this.productRepository = productRepository;
        this.ownerRepository = ownerRepository;
    }

    @Transactional
    public ProductDetailResponse createProduct(final Long ownerId, final ProductCreateServiceRequest request) {
        validateOwner(ownerId);

        Owner foundOwner = ownerRepository.getById(ownerId);
        Product saveProduct = request.toEntity(foundOwner, request);
        productRepository.save(saveProduct);

        return ProductDetailResponse.of(saveProduct);
    }

    private void validateOwner(final Long ownerId) {
        if (!ownerRepository.existsById(ownerId)) {
            throw new NotFoundOwnerException("존재하지 않는 사장님 입니다.");
        }
    }
}
