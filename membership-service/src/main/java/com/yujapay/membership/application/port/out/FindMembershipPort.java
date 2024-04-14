package com.yujapay.membership.application.port.out;

import com.yujapay.membership.domain.Membership;

public interface FindMembershipPort {

    Membership findMembership(
            Membership.MembershipId membershipId
    );

}
