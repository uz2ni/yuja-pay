package com.yujapay.membership.application.port.in;

import com.yujapay.membership.domain.Membership;

public interface FindMembershipUseCase {

    Membership findMembership(FindMembershipCommand command);

}