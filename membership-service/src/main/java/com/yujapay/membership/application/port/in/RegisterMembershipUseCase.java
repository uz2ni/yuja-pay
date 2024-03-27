package com.yujapay.membership.application.port.in;

import com.yujapay.membership.domain.Membership;

public interface RegisterMembershipUseCase {

    Membership registerMembership(RegisterMembershipCommand command);

}
