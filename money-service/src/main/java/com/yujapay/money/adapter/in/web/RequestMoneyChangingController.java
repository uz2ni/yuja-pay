package com.yujapay.money.adapter.in.web;

import com.yujapay.common.WebAdapter;
import com.yujapay.money.application.port.in.IncreaseMoneyResultCommand;
import com.yujapay.money.application.port.in.IncreaseMoneyResultUseCase;
import com.yujapay.money.domain.MoneyChangingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestMoneyChangingController {

    private final IncreaseMoneyResultUseCase increaseMoneyResultUseCase;

    // private final DecreaseMoneyResultUseCase decreaseMoneyResultUseCase;

    @PostMapping(path = "/money/increase")
    MoneyChangeResultDetail increaseMoneyChangeRequest(@RequestBody IncreaseMoneyChangingRequest request) {

        IncreaseMoneyResultCommand command = IncreaseMoneyResultCommand.builder()
                .targetMembershipId(request.getTargetMembershipId())
                .amount(request.getAmount())
                .build();

        MoneyChangingRequest moneyChangingRequest = increaseMoneyResultUseCase.increaseMoneyRequest(command);
        // MoneyChangingRequest -> MoneyChangeResultDetail
        MoneyChangeResultDetail resultDetail = new MoneyChangeResultDetail(
                moneyChangingRequest.getMoneyChangingRequestId(),
                0,
                0,
                moneyChangingRequest.getChangingMoneyAmount()
        );

        return resultDetail;
    }

    @PostMapping(path = "/money/increase-async")
    MoneyChangeResultDetail increaseMoneyChangeRequestAsync(@RequestBody IncreaseMoneyChangingRequest request) {

        IncreaseMoneyResultCommand command = IncreaseMoneyResultCommand.builder()
                .targetMembershipId(request.getTargetMembershipId())
                .amount(request.getAmount())
                .build();

        MoneyChangingRequest moneyChangingRequest = increaseMoneyResultUseCase.increaseMoneyRequestAsync(command);
        // MoneyChangingRequest -> MoneyChangeResultDetail
        MoneyChangeResultDetail resultDetail = new MoneyChangeResultDetail(
                moneyChangingRequest.getMoneyChangingRequestId(),
                0,
                0,
                moneyChangingRequest.getChangingMoneyAmount()
        );

        return resultDetail;
    }

    @PostMapping(path = "/money/decrease")
    MoneyChangeResultDetail decreaseMoneyChangeRequest(@RequestBody DecreaseMoneyChangingRequest request) {
//
//        RegisterBankAccountCommand command = RegisterBankAccountCommand.builder()
//                .membershipId(request.getMembershipId())
//                .bankName(request.getBankName())
//                .bankAccountNumber(request.getBankAccountNumber())
//                .linkedStatusIsValid(request.isValid())
//                .build();
//
//        RegisteredBankAccount registeredBankAccount = registerBankAccountUseCase.registerBankAccount(command);
//
//        if(registeredBankAccount == null) {
//            // TODO: Error handling
//            return null;
//        }
//        return registeredBankAccount;
        return null;
    }
}