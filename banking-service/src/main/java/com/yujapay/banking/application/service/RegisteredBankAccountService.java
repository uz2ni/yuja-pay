package com.yujapay.banking.application.service;

import com.yujapay.banking.adapter.out.persistence.RegisteredBankAccountJpaEntity;
import com.yujapay.banking.adapter.out.persistence.RegisteredBankAccountMapper;
import com.yujapay.banking.application.port.in.RegisterBankAccountCommand;
import com.yujapay.banking.application.port.in.RegisterBankAccountUseCase;
import com.yujapay.banking.application.port.out.RegisterBankAccountPort;
import com.yujapay.banking.domain.RegisteredBankAccount;
import com.yujapay.common.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class RegisteredBankAccountService implements RegisterBankAccountUseCase {

    private final RegisterBankAccountPort registerBankAccountPort;
    private final RegisteredBankAccountMapper registeredBankAccountMapper;

    @Override
    public RegisteredBankAccount registerBankAccount(RegisterBankAccountCommand command) {

        // 은행 계좌를 등록해야하는 서비스 (비즈니스 로직)
        // command.getMembershipId()

        // (멤버 서비스도 확인?) 여기서는 skip

        // 1. 외부 실제 은행에 등록이 가능한 계좌인지(정상인지) 확인한다.
        // 외부의 은행에 이 계좌 정상인지? 확인을 해야해요.
        // Biz Logic -> External System
        // Port -> Adapter -> External System
        // Port
        // 실제 외부의 은행계좌 정보를 Get

        return null;
    }
}
