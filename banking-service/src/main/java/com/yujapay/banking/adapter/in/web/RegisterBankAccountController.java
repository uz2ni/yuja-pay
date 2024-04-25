package com.yujapay.banking.adapter.in.web;

import com.yujapay.banking.application.port.in.RegisterBankAccountCommand;
import com.yujapay.banking.application.port.in.RegisterBankAccountUseCase;
import com.yujapay.banking.domain.RegisteredBankAccount;
import com.yujapay.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter // 헥사고널 아키텍쳐에서 webAdaptor 의 용도를 의미하는 논리적인 것 (기능적인 것은 x)
@RestController
@RequiredArgsConstructor
public class RegisterBankAccountController {

    private final RegisterBankAccountUseCase registerBankAccountUseCase;

    @PostMapping(path = "/membership/register")
    RegisteredBankAccount registerBankAccount(@RequestBody RegisterBankAccountRequest request) {

        RegisterBankAccountCommand command = RegisterBankAccountCommand.builder()
                .membershipId(request.getMembershipId())
                .bankName(request.getBankName())
                .bankAccountNumber(request.getBankAccountNumber())
                .linkedStatusIsValid(request.isLinkedStatusIsValid())
                .build();

        return registerBankAccountUseCase.registerBankAccount(command);
    }
}