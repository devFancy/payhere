package com.payhere.product.domain.entity;

import com.payhere.common.BaseEntity;
import com.payhere.owner.domain.entity.Owner;
import com.payhere.product.domain.ChoSungQuery;
import com.payhere.product.domain.ProductCategory;
import com.payhere.product.domain.ProductSize;
import com.payhere.product.exception.InvalidProductException;
import lombok.Builder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "products")
@Entity
public class Product extends BaseEntity {

    private static final int MAX_DESCRIPTION_LENGTH = 255;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @Column(name = "product_category", nullable = false)
    private ProductCategory productCategory;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "cost", nullable = false)
    private int cost;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "name_init", nullable = false)
    private String nameInit;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "barcode", nullable = false)
    private String barcode;

    @Column(name = "expiration_date", nullable = false)
    private LocalDateTime expirationDate;

    @Column(name = "product_size", nullable = false)
    private ProductSize productSize;

    protected Product() {
    }

    @Builder
    public Product(final Owner owner, final ProductCategory productCategory, final int price, final int cost
            , final String name, final String description, final String barcode
            , final LocalDateTime expirationDate, final ProductSize productSize) {
        validatePrice(price);
        validateCost(cost);
        validateDescriptionLength(description);
        this.owner = owner;
        this.productCategory = productCategory;
        this.price = price;
        this.cost = cost;
        this.name = name;
        this.nameInit = ChoSungQuery.extractChoSung(name);
        this.description = description;
        this.barcode = barcode;
        this.expirationDate = expirationDate;
        this.productSize = productSize;
    }

    public void change(final Owner owner, final ProductCategory productCategory, final int price, final int cost,
                       final String name, final String description, final String barcode,
                       final LocalDateTime expirationDate, final ProductSize productSize) {
        validatePrice(price);
        validateCost(cost);
        validateDescriptionLength(description);
        this.owner = owner;
        this.productCategory = productCategory;
        this.price = price;
        this.cost = cost;
        this.name = name;
        this.nameInit = ChoSungQuery.extractChoSung(name);
        this.description = description;
        this.barcode = barcode;
        this.expirationDate = expirationDate;
        this.productSize = productSize;
    }

    public boolean isOwner(final Long accessOwnerId) {
        if (accessOwnerId == null) {
            return false;
        }
        return owner.getId().equals(accessOwnerId);
    }

    private void validatePrice(final int price) {
        if (price <= 0) {
            throw new InvalidProductException("상품 가격은 0원 이상이어야 합니다.");
        }
    }

    private void validateCost(final int cost) {
        if (cost <= 0) {
            throw new InvalidProductException("상품 원가는 0원 이상이어야 합니다.");
        }
    }

    private void validateDescriptionLength(final String description) {
        if (description.isBlank()) {
            throw new InvalidProductException("상품 설명은 공백일 수 없습니다.");
        }
        if (description.length() > MAX_DESCRIPTION_LENGTH) {
            throw new InvalidProductException(String.format("상품 설명의 길이는 %d를 초과할 수 없습니다.", MAX_DESCRIPTION_LENGTH));
        }
    }

    public Long getId() {
        return id;
    }

    public Owner getOwner() {
        return owner;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public int getPrice() {
        return price;
    }

    public int getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getBarcode() {
        return barcode;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }


    public ProductSize getProductSize() {
        return productSize;
    }
}
