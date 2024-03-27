package com.yujapay.membership.adapter.in.web;

import com.yujapay.membership.application.port.in.RegisterMembershipCommand;
import com.yujapay.membership.application.port.in.RegisterMembershipUseCase;
import com.yujapay.membership.domain.Membership;
import common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter // 헥사고널 아키텍쳐에서 webAdaptor 의 용도를 의미하는 논리적인 것 (기능적인 것은 x)
@RestController
@RequiredArgsConstructor
public class RegisterMembershipController {

    RegisterMembershipUseCase registerMembershipUseCase;

    @PostMapping(path = "/membership/register")
    Membership registerMembership(@RequestBody RegisterMembershipRequest request) {

        RegisterMembershipCommand command = RegisterMembershipCommand.builder()
                .name(request.getName())
                .address(request.getAddress())
                .email(request.getEmail())
                .isValid(true)
                .isCorp(request.isCorp())
                .build();

        return registerMembershipUseCase.registerMembership(command);
    }
}