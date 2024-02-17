package com.payhere.common.fixtures;

import com.payhere.owner.domain.CellPhoneNumber;
import com.payhere.owner.domain.Password;
import com.payhere.owner.domain.entity.Owner;

public class OwnerFixtures {

    /* 사장님 */
    public static final Long OWNER_ID = 1L;
    public static final String 사장님_휴대폰_번호 = "010-1234-5678";
    public static final String 사장님_비밀번호 = "qwer1234!!";

    public static Owner 사장님() {
        return Owner.builder()
                .cellPhoneNumber(new CellPhoneNumber(사장님_휴대폰_번호))
                .password(new Password(사장님_비밀번호))
                .build();
    }
}
