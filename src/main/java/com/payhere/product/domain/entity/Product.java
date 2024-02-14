package com.payhere.product.domain.entity;

import com.payhere.owner.domain.entity.Owner;
import com.payhere.product.domain.ProductCategory;
import com.payhere.product.domain.ProductSize;
import lombok.Builder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "products")
@Entity
public class Product {

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
        this.owner = owner;
        this.productCategory = productCategory;
        this.price = price;
        this.cost = cost;
        this.name = name;
        this.description = description;
        this.barcode = barcode;
        this.expirationDate = expirationDate;
        this.productSize = productSize;
    }

    public void change(final Owner owner, final ProductCategory productCategory, final int price, final int cost,
                       final String name, final String description, final String barcode,
                       final LocalDateTime expirationDate, final ProductSize productSize) {
        this.owner = owner;
        this.productCategory = productCategory;
        this.price = price;
        this.cost = cost;
        this.name = name;
        this.description = description;
        this.barcode = barcode;
        this.expirationDate = expirationDate;
        this.productSize = productSize;
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
