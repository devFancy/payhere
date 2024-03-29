package com.payhere.auth.domain.entity;

import com.payhere.global.BaseEntity;
import com.payhere.owner.domain.entity.Owner;

import javax.persistence.*;

@Table(name = "auth_tokens")
@Entity
public class AuthToken extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Owner owner;

    @Column(name = "token")
    private String token;

    protected AuthToken() {
    }

    public AuthToken(final Owner owner, final String token) {
        this.owner = owner;
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public Owner getOwner() {
        return owner;
    }

    public String getToken() {
        return token;
    }
}
