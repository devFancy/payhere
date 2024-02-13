package com.payhere.owner.domain.entity;


import com.payhere.owner.domain.CellPhoneNumber;
import com.payhere.owner.domain.Password;

import javax.persistence.*;

@Table(name = "owners")
@Entity
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "cell_phone_number", nullable = false)
    @Embedded
    private CellPhoneNumber cellPhoneNumber;

    @Column(name = "password", nullable = false)
    @Embedded
    private Password password;

    protected Owner() {
    }

    public Owner(final CellPhoneNumber cellPhoneNumber, final Password password) {
        this.cellPhoneNumber = cellPhoneNumber;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public CellPhoneNumber getCellPhoneNumber() {
        return cellPhoneNumber;
    }

    public Password getPassword() {
        return password;
    }
}
