package com.yujapay.membership.application.service;

import com.yujapay.membership.adapter.out.persistence.MembershipJpaEntity;
import com.yujapay.membership.adapter.out.persistence.MembershipMapper;
import com.yujapay.membership.application.port.in.ModifyMembershipCommand;
import com.yujapay.membership.application.port.in.ModifyMembershipUseCase;
import com.yujapay.membership.application.port.in.RegisterMembershipCommand;
import com.yujapay.membership.application.port.in.RegisterMembershipUseCase;
import com.yujapay.membership.application.port.out.ModifyMembershipPort;
import com.yujapay.membership.application.port.out.RegisterMembershipPort;
import com.yujapay.membership.domain.Membership;
import common.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class ModifyMembershipService implements ModifyMembershipUseCase {

    private final ModifyMembershipPort modifyMembershipPort;
    private final MembershipMapper membershipMapper;

    @Override
    public Membership modifyMembership(ModifyMembershipCommand command) {
        MembershipJpaEntity jpaEntity = modifyMembershipPort.modifyMembership(
                new Membership.MembershipId(command.getMembershipId()),
                new Membership.MembershipName(command.getName()),
                new Membership.MembershipEmail(command.getEmail()),
                new Membership.MembershipAddress(command.getAddress()),
                new Membership.MembershipIsValid(command.isValid()),
                new Membership.MembershipIsCorp(command.isCorp())
        );

        return membershipMapper.mapToDomainEntity(jpaEntity);
    }
}
