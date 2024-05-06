package com.yujapay.banking.application.service;

import com.yujapay.banking.adapter.out.external.bank.BankAccount;
import com.yujapay.banking.adapter.out.external.bank.ExternalFirmbankingRequest;
import com.yujapay.banking.adapter.out.external.bank.FirmbankingResult;
import com.yujapay.banking.adapter.out.external.bank.GetBankAccountRequest;
import com.yujapay.banking.adapter.out.persistence.FirmBankingRequestJpaEntity;
import com.yujapay.banking.adapter.out.persistence.FirmbankingRequestMapper;
import com.yujapay.banking.adapter.out.persistence.RegisteredBankAccountJpaEntity;
import com.yujapay.banking.adapter.out.persistence.RegisteredBankAccountMapper;
import com.yujapay.banking.application.port.in.FirmbankingRequestCommand;
import com.yujapay.banking.application.port.in.RegisterBankAccountCommand;
import com.yujapay.banking.application.port.in.RegisterBankAccountUseCase;
import com.yujapay.banking.application.port.in.RequestFirmbankingUseCase;
import com.yujapay.banking.application.port.out.RegisterBankAccountPort;
import com.yujapay.banking.application.port.out.RequestBankAccountInfoPort;
import com.yujapay.banking.application.port.out.RequestExternalFirmbankingPort;
import com.yujapay.banking.application.port.out.RequestFirmbankingPort;
import com.yujapay.banking.domain.FirmbankingRequest;
import com.yujapay.banking.domain.RegisteredBankAccount;
import com.yujapay.common.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
@Transactional
public class RequestFirmbankingService implements RequestFirmbankingUseCase {

    private final FirmbankingRequestMapper mapper;

    private final RequestFirmbankingPort requestFirmbankingPort;

    private final RequestExternalFirmbankingPort requestExternalFirmbankingPort;


    @Override
    public FirmbankingRequest requestFirmbanking(FirmbankingRequestCommand command) {

        // a -> b 계좌

        // 1. 요청에 대해 정보를 먼저 write. "요청" 상태로
        FirmBankingRequestJpaEntity requestedEntity = requestFirmbankingPort.createFirmbankingRequest(
                new FirmbankingRequest.FromBankName(command.getFromBankName()),
                new FirmbankingRequest.FromBankAccountNumber(command.getFromBankAccountNumber()),
                new FirmbankingRequest.ToBankName(command.getToBankName()),
                new FirmbankingRequest.ToBankAccountNumber(command.getToBankAccountNumber()),
                new FirmbankingRequest.MoneyAmount(command.getMoneyAmount()),
                new FirmbankingRequest.FirmbankingStatus(0)
        );

        // 2. 외부 은행에 펌뱅킹 요청
        FirmbankingResult result = requestExternalFirmbankingPort.requestExternalFirmbanking(new ExternalFirmbankingRequest(
                command.getFromBankName(),
                command.getFromBankAccountNumber(),
                command.getToBankName(),
                command.getToBankAccountNumber()
        ));

        // Transactional UUID
        UUID randomUUID = UUID.randomUUID();
        requestedEntity.setUuid(randomUUID.toString());
        // 3. 결과에 따라서 1번에서 작성했던 FirmbankingRequest 정보를 Update
        if (result.getResultCode() == 0) {
            // 성공
            requestedEntity.setFirmbankingStatus(1);
        }else {
            // 실패
            requestedEntity.setFirmbankingStatus(2);
        }

        // 4. 결과를 리턴하기 전에 바뀐 상태 값을 기준으로 다시 save
        return mapper.mapToDomainEntity(requestFirmbankingPort.modifyFirmbankingRequest(requestedEntity), randomUUID);
    }
}
