package com.yujapay.banking.application.service;

import com.yujapay.banking.adapter.out.external.bank.BankAccount;
import com.yujapay.banking.adapter.out.external.bank.GetBankAccountRequest;
import com.yujapay.banking.adapter.out.persistence.RegisteredBankAccountJpaEntity;
import com.yujapay.banking.adapter.out.persistence.RegisteredBankAccountMapper;
import com.yujapay.banking.application.port.in.RegisterBankAccountCommand;
import com.yujapay.banking.application.port.in.RegisterBankAccountUseCase;
import com.yujapay.banking.application.port.out.GetMembershipPort;
import com.yujapay.banking.application.port.out.MembershipStatus;
import com.yujapay.banking.application.port.out.RegisterBankAccountPort;
import com.yujapay.banking.application.port.out.RequestBankAccountInfoPort;
import com.yujapay.banking.domain.RegisteredBankAccount;
import com.yujapay.common.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class RegisteredBankAccountService implements RegisterBankAccountUseCase {

    private final GetMembershipPort getMembershipPort;
    private final RegisterBankAccountPort registerBankAccountPort;
    private final RegisteredBankAccountMapper mapper;
    private final RequestBankAccountInfoPort requestBankAccountInfoPort;

    @Override
    public RegisteredBankAccount registerBankAccount(RegisterBankAccountCommand command) {

        // 은행 계좌를 등록해야하는 서비스 (비즈니스 로직)
        // command.getMembershipId() // 이 멤버십 계좌가 유효하니? 라고 membership 서비스에 물어봐야 함. 일단 생략.(유효하다고 가정)

        // (멤버 서비스도 확인?) 여기서는 skip
        // call membership svc, 정상인지 확인
        MembershipStatus membershipStatus = getMembershipPort.getMembership(command.getMembershipId());
        if(!membershipStatus.isValid()) {
            return null;
        }

        // 1. 외부 실제 은행에 등록이 가능한 계좌인지(정상인지) 확인한다.
        // 외부의 은행에 이 계좌 정상인지? 확인을 해야해요.
        // Biz Logic -> External System
        // Port -> Adapter -> External System
        // Port

        // 실제 외부의 은행계좌 정보를 Get
        BankAccount bankAccountInfo = requestBankAccountInfoPort.getBankAccountInfo(new GetBankAccountRequest(command.getBankName(), command.getBankAccountNumber()));
        boolean accountIsValid = bankAccountInfo.isValid();

        // 2. 등록 가능한 계좌라면 등록한다. 성공하면 등록 정보 리턴
        // 2-1. 등록 가능하지 않은 계좌라면 에러 리턴
        if(accountIsValid) {
            // 등록 정보 저장
            RegisteredBankAccountJpaEntity saveAccountInfo = registerBankAccountPort.createRegisteredBankAccount(
                    new RegisteredBankAccount.MembershipId(command.getMembershipId()+""),
                    new RegisteredBankAccount.BankName(command.getBankName()),
                    new RegisteredBankAccount.BankAccountNumber(command.getBankAccountNumber()),
                    new RegisteredBankAccount.LinkedStatusIsValid(command.isLinkedStatusIsValid())
            );
            return mapper.mapToDomainEntity(saveAccountInfo);
        }else {
            return null;
        }
    }
}
