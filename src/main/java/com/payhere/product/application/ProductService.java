package com.payhere.product.application;


import com.payhere.auth.dto.LoginOwner;
import com.payhere.auth.exception.AuthorizationException;
import com.payhere.owner.domain.OwnerRepository;
import com.payhere.owner.domain.entity.Owner;
import com.payhere.owner.exception.NotFoundOwnerException;
import com.payhere.product.domain.ProductRepository;
import com.payhere.product.domain.entity.Product;
import com.payhere.product.dto.request.ProductCreateServiceRequest;
import com.payhere.product.dto.request.ProductUpdateServiceRequest;
import com.payhere.product.dto.response.ProductDetailResponse;
import com.payhere.product.exception.NotFoundProductException;
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
        validateOwnerExists(ownerId);

        Owner foundOwner = ownerRepository.getById(ownerId);
        Product saveProduct = request.toEntity(foundOwner, request);
        productRepository.save(saveProduct);

        return ProductDetailResponse.of(saveProduct);
    }

    private void validateOwnerExists(final Long ownerId) {
        if (!ownerRepository.existsById(ownerId)) {
            throw new NotFoundOwnerException("존재하지 않는 사장님 입니다.");
        }
    }

    @Transactional
    public void updateProduct(final LoginOwner loginOwner, final Long productId, final ProductUpdateServiceRequest request) {
        Owner owner = ownerRepository.getById(loginOwner.getId());
        Product product = findProductObject(productId);
        validateProductOwnership(loginOwner, product);

        product.change(owner, request.getProductCategory(), request.getPrice(), request.getCost(), request.getName()
        , request.getDescription(), request.getBarcode(), request.getExpirationDate(), request.getProductSize());
    }

    private Product findProductObject(final Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(NotFoundProductException::new);
    }

    @Transactional
    public void deleteProduct(final LoginOwner loginOwner, final Long productId) {
        Product product = findProductObject(loginOwner.getId());
        validateProductOwnership(loginOwner, product);

        productRepository.deleteById(productId);
    }

    private void validateProductOwnership(final LoginOwner loginOwner, final Product product) {
        if(!product.isOwner(loginOwner.getId())) {
            throw new AuthorizationException();
        }
    }
}
