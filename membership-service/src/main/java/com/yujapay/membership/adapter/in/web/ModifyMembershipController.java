package com.yujapay.membership.adapter.in.web;

import com.yujapay.common.WebAdapter;
import com.yujapay.membership.application.port.in.ModifyMembershipCommand;
import com.yujapay.membership.application.port.in.ModifyMembershipUseCase;
import com.yujapay.membership.domain.Membership;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class ModifyMembershipController {

    private final ModifyMembershipUseCase modifyMembershipUseCase;

    @PostMapping(path = "/membership/modify/{membershipId}")
    Membership modifyMembership(@RequestBody ModifyMembershipRequest request) {

        ModifyMembershipCommand command = ModifyMembershipCommand.builder()
                .membershipId(request.getMembershipId())
                .name(request.getName())
                .address(request.getAddress())
                .email(request.getEmail())
                .isValid(request.isValid())
                .isCorp(request.isCorp())
                .build();

        return modifyMembershipUseCase.modifyMembership(command);
    }
}