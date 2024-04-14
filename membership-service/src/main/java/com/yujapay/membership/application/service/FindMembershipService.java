package com.yujapay.membership.application.service;

import com.yujapay.membership.adapter.out.persistence.MembershipMapper;
import com.yujapay.membership.application.port.in.FindMembershipCommand;
import com.yujapay.membership.application.port.in.FindMembershipUseCase;
import com.yujapay.membership.application.port.out.FindMembershipPort;
import com.yujapay.membership.domain.Membership;
import common.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class FindMembershipService implements FindMembershipUseCase {

    private final FindMembershipPort findMembershipPort;
    private final MembershipMapper membershipMapper;

    @Override
    public Membership findMembership(FindMembershipCommand command) {
        return findMembershipPort.findMembership(new Membership.MembershipId(command.getMembershipId()));
    }

}
