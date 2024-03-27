package com.yujapay.membership.application.port.out;

import com.yujapay.membership.adapter.out.persistence.MembershipJpaEntity;
import com.yujapay.membership.domain.Membership;

public interface RegisterMembershipPort { // Adaptor 는 이 Port 인터페이스를 구현하는 구현체가 될 것임

    MembershipJpaEntity createMembership(
            Membership.MembershipName membershipName
            , Membership.MembershipEmail membershipEmail
            , Membership.MembershipAddress membershipAddress
            , Membership.MembershipIsValid membershipIsValid
            , Membership.MembershipIsCorp membershipIsCorp
    );
}
