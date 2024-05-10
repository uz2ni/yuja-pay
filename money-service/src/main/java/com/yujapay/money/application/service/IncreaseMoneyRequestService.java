package com.yujapay.money.application.service;

import com.yujapay.common.UseCase;
import com.yujapay.money.adapter.out.persistence.MemberMoneyJpaEntity;
import com.yujapay.money.adapter.out.persistence.MoneyChangingRequestJpaEntity;
import com.yujapay.money.adapter.out.persistence.MoneyChangingRequestMapper;
import com.yujapay.money.application.port.in.IncreaseMoneyResultCommand;
import com.yujapay.money.application.port.in.IncreaseMoneyResultUseCase;
import com.yujapay.money.application.port.out.IncreaseMoneyPort;
import com.yujapay.money.domain.MemberMoney;
import com.yujapay.money.domain.MoneyChangingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
@Transactional
public class IncreaseMoneyRequestService implements IncreaseMoneyResultUseCase {

    private final IncreaseMoneyPort increaseMoneyPort;
    private final MoneyChangingRequestMapper mapper;

    @Override
    public MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyResultCommand command) {

        // 1. 고객 정보가 정상인지 확인 (멤버)

        // 2. 고객의 연동 계좌 있는지, 잔액 충분한지 확인 (뱅킹)

        // 3. 법인 계좌 상태 정상인지 확인 (뱅킹)

        // 4. 증액을 위한 "기록". 요청 상태로 MoneyChangingRequest 생성 (MoneyChangingRequest)

        // 5. 펌뱅킹 수행 (고객의 연동 계좌 -> 유자페이 법인 계좌) (뱅킹)

        // 6-1. 결과 정상이면, 성공으로 상태값 변동 후 리턴
        // 성공 시 memberMoney 값 증액 필요
        MemberMoneyJpaEntity memberMoneyJpaEntity = increaseMoneyPort.increaseMoney(
                new MemberMoney.MembershipId(command.getTargetMembershipId()),
                command.getAmount()
        );

        if(memberMoneyJpaEntity != null) {
            return mapper.mapToDomainEntity(increaseMoneyPort.createMoneyChangingRequest(
                    new MoneyChangingRequest.TargetMembershipId(command.getTargetMembershipId()),
                    new MoneyChangingRequest.MoneyChangingType(1),
                    new MoneyChangingRequest.ChangingMoneyAmount(command.getAmount()),
                    new MoneyChangingRequest.MoneyChangingMoneyStatus(1),
                    new MoneyChangingRequest.Uuid(UUID.randomUUID().toString())
            ));
        }

        // 6-2. 결과 실패이면, 실패로 상태값 변동 후 리턴
        return null;


    }


//
//    @Override
//    public RegisteredBankAccount registerBankAccount(RegisterBankAccountCommand command) {
//
//        // 은행 계좌를 등록해야하는 서비스 (비즈니스 로직)
//        // command.getMembershipId() // 이 멤버십 계좌가 유효하니? 라고 membership 서비스에 물어봐야 함. 일단 생략.(유효하다고 가정)
//
//        // (멤버 서비스도 확인?) 여기서는 skip
//
//        // 1. 외부 실제 은행에 등록이 가능한 계좌인지(정상인지) 확인한다.
//        // 외부의 은행에 이 계좌 정상인지? 확인을 해야해요.
//        // Biz Logic -> External System
//        // Port -> Adapter -> External System
//        // Port
//
//        // 실제 외부의 은행계좌 정보를 Get
//        BankAccount bankAccountInfo = requestBankAccountInfoPort.getBankAccountInfo(new GetBankAccountRequest(command.getBankName(), command.getBankAccountNumber()));
//        boolean accountIsValid = bankAccountInfo.isValid();
//
//        // 2. 등록 가능한 계좌라면 등록한다. 성공하면 등록 정보 리턴
//        // 2-1. 등록 가능하지 않은 계좌라면 에러 리턴
//        if(accountIsValid) {
//            // 등록 정보 저장
//            RegisteredBankAccountJpaEntity saveAccountInfo = registerBankAccountPort.createRegisteredBankAccount(
//                    new RegisteredBankAccount.MembershipId(command.getMembershipId()+""),
//                    new RegisteredBankAccount.BankName(command.getBankName()),
//                    new RegisteredBankAccount.BankAccountNumber(command.getBankAccountNumber()),
//                    new RegisteredBankAccount.LinkedStatusIsValid(command.isLinkedStatusIsValid())
//            );
//            return mapper.mapToDomainEntity(saveAccountInfo);
//        }else {
//            return null;
//        }
//    }
}
