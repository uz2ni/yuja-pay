package com.yujapay.membership.application.service;

import com.yujapay.common.UseCase;
import com.yujapay.membership.adapter.out.persistence.MembershipJpaEntity;
import com.yujapay.membership.adapter.out.persistence.MembershipMapper;
import com.yujapay.membership.application.port.in.RegisterMembershipCommand;
import com.yujapay.membership.application.port.in.RegisterMembershipUseCase;
import com.yujapay.membership.application.port.out.RegisterMembershipPort;
import com.yujapay.membership.domain.Membership;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class RegisterMembershipService implements RegisterMembershipUseCase {

    private final RegisterMembershipPort registerMembershipPort;
    private final MembershipMapper membershipMapper;

    @Override
    public Membership registerMembership(RegisterMembershipCommand command) {

        // command -> DB

        // biz logic -> DB
        // 비즈니스 로직에서 db로 나가려면 외부 시스템(external system)이다. port, adaptor 가 필요 하다.
        MembershipJpaEntity jpaEntity = registerMembershipPort.createMembership(
                new Membership.MembershipName(command.getName()),
                new Membership.MembershipEmail(command.getEmail()),
                new Membership.MembershipAddress(command.getAddress()),
                new Membership.MembershipIsValid(command.isValid()),
                new Membership.MembershipIsCorp(command.isCorp())
        );
        // entity -> membership
        return membershipMapper.mapToDomainEntity(jpaEntity);
    }
}
